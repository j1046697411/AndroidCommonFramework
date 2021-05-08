package org.jzl.android.recyclerview.util.datablock.impl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jzl.android.recyclerview.util.Logger;
import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockFactory;
import org.jzl.android.recyclerview.util.datablock.DataBlockObserver;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.DataSource;
import org.jzl.android.recyclerview.util.datablock.OnDataBlockChangedCallback;
import org.jzl.android.recyclerview.util.datablock.PositionType;
import org.jzl.lang.util.CollectionUtils;
import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataBlockProviderImpl<T> extends AbstractDataSource<T> implements DataBlockProvider<T> {

    private static final Logger log = Logger.logger(DataBlockProvider.class);

    private static final Comparator<DataBlock<?>> COMPARATOR = (o1, o2) -> {

        int sort = Integer.compare(o1.getPositionType().sequence, o2.getPositionType().sequence);
        if (sort == 0) {
            return Integer.compare(o1.getSortOrder(), o2.getSortOrder());
        } else {
            return sort;
        }
    };
    private final TreeSet<DataBlock<T>> dataBlocks = new TreeSet<>(COMPARATOR);
    private final NavigableSet<DataBlock<T>> descendingSet = dataBlocks.descendingSet();

    private final List<T> oldList = new ArrayList<>();
    private final Set<DataBlock<T>> unmodifiableDataBlocks = Collections.unmodifiableSet(dataBlocks);
    private final DataBlockObserver<T, DataBlockProvider<T>> dataBlockObserver;
    private final int defaultDataBlockId;
    private final DataBlockFactory<T> dataBlockFactory;
    private final AtomicBoolean isDirtyData = new AtomicBoolean(false);

    public DataBlockProviderImpl(int defaultDataBlockId, @NonNull DataBlockFactory<T> dataBlockFactory) {
        this.dataBlockObserver = new DataBlockObserverImpl<>(this);
        this.defaultDataBlockId = defaultDataBlockId;
        this.dataBlockFactory = dataBlockFactory;
        dataBlock(PositionType.CONTENT, defaultDataBlockId);
    }

    @NonNull
    @Override
    public Collection<DataBlock<T>> getDataBlocks() {
        return unmodifiableDataBlocks;
    }

    @NonNull
    @Override
    public DataBlockObserver<T, DataBlockProvider<T>> getDataBlockObserver() {
        return dataBlockObserver;
    }

    @Override
    public void addOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, DataBlockProvider<T>> dataBlockChangedCallback) {
        this.dataBlockObserver.addOnDataBlockChangedCallback(dataBlockChangedCallback);
    }

    @Override
    public void removeOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, DataBlockProvider<T>> dataBlockChangedCallback) {
        this.dataBlockObserver.removeOnDataBlockChangedCallback(dataBlockChangedCallback);
    }

    @Override
    public void addDataBlock(@NonNull DataBlock<T> dataBlock) {
        DataBlock<T> oldDataBlock = findDataBlockByPositionTypeAndDataBlockId(dataBlock.getPositionType(), dataBlock.getDataBlockId());
        if (oldDataBlock == dataBlock) {
            return;
        }
        if (ObjectUtils.nonNull(oldDataBlock)) {
            this.removeDataBlock(oldDataBlock);
            dataBlock.addAll(0, oldDataBlock);
        }
        dataBlock.bindDataBlockProvider(this);
        this.dataBlocks.add(dataBlock);
        int startPosition = dataBlock.getStartPosition();
        dataBlockObserver.notifyDataRangeInserted(startPosition, dataBlock.size());
    }

    @Override
    public void addDataBlockAll(@NonNull Collection<DataBlock<T>> dataBlocks) {
        ForeachUtils.each(dataBlocks, this::addDataBlock);
    }

    @Override
    public void removeDataBlock(@NonNull DataBlock<T> dataBlock) {
        int startPosition = dataBlock.getStartPosition();
        if (this.dataBlocks.remove(dataBlock)) {
            dataBlock.unbindDataBlockProvider();
            dataBlockObserver.notifyDataRangeRemoved(startPosition, dataBlock.size());
        }
    }

    @Override
    public void removeDataBlockAll(@NonNull Collection<DataBlock<T>> dataBlocks) {
        ForeachUtils.each(dataBlocks, this::removeDataBlock);
    }

    @Override
    public DataBlock<T> dataBlock(PositionType positionType, int dataBlockId) {
        DataBlock<T> dataBlock = findDataBlockByPositionTypeAndDataBlockId(positionType, dataBlockId);
        if (ObjectUtils.isNull(dataBlock)) {
            dataBlock = dataBlockFactory.createDataBlock(this, positionType, dataBlockId);
            addDataBlock(dataBlock);
        }
        return dataBlock;
    }

    @Override
    public DataBlock<T> defaultDataBlock() {
        return dataBlock(PositionType.CONTENT, defaultDataBlockId);
    }

    @Override
    public DataBlock<T> lastDataBlock() {
        return dataBlocks.last();
    }

    @Override
    public DataBlock<T> lastContentDataBlock() {
        return ForeachUtils.findByOne(this.descendingSet, target -> target.getPositionType() == PositionType.CONTENT);
    }

    @Override
    public Set<DataBlock<T>> findDataBlockByPositionType(PositionType positionType) {
        Set<DataBlock<T>> dataBlocks = new TreeSet<>(COMPARATOR);
        ForeachUtils.each(this.dataBlocks, target -> {
            if (target.getPositionType() == positionType) {
                dataBlocks.add(target);
            }
        });
        return dataBlocks;
    }

    @Override
    public DataBlock<T> findDataBlockByPositionTypeAndDataBlockId(PositionType positionType, int dataBlockId) {
        return ForeachUtils.findByOne(this.dataBlocks, target -> target.getDataBlockId() == dataBlockId && target.getPositionType() == positionType);
    }

    @Override
    public DataBlock<T> findDataBlockByPosition(int position) {
        for (DataBlock<T> dataBlock : this.dataBlocks) {
            if (position < dataBlock.size()) {
                return dataBlock;
            } else {
                position -= dataBlock.size();
            }
        }
        return null;
    }

    @Override
    public boolean add(T t) {
        return lastDataBlock().add(t);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        return lastDataBlock().addAll(c);
    }

    @Override
    public void add(int index, T element) {
        DataBlock<T> dataBlock = findDataBlockByPosition(index);
        if (ObjectUtils.nonNull(dataBlock)) {
            dataBlock.add(index - dataBlock.getStartPosition(), element);
        }
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        DataBlock<T> dataBlock = findDataBlockByPosition(index);
        if (ObjectUtils.nonNull(dataBlock)) {
            return dataBlock.addAll(index - dataBlock.getStartPosition(), c);
        }
        return false;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            return ObjectUtils.nonNull(remove(index));
        }
        return false;
    }

    @Override
    public T remove(int index) {
        DataBlock<T> dataBlock = findDataBlockByPosition(index);
        if (ObjectUtils.nonNull(dataBlock)) {
            Log.i("MainActivity", "index -> " + index + ',' + dataBlock.getStartPosition());
            return dataBlock.remove(index - dataBlock.getStartPosition());
        }
        return null;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        ForeachUtils.each(c, this::remove);
        return true;
    }

    @Override
    public void clear() {
        if (isEmpty()){
            return;
        }
        int size = size();
        dataBlockObserver.disableDataChangedNotify();
        ForeachUtils.each(dataBlocks, List::clear);
        dataBlockObserver.enableDataChangedNotify();
        dataBlockObserver.notifyDataRangeRemoved(0, size);
    }

    @Override
    public T set(int index, T element) {
        DataBlock<T> dataBlock = findDataBlockByPosition(index);
        if (ObjectUtils.nonNull(dataBlock)) {
            return dataBlock.set(index - dataBlock.getStartPosition(), element);
        }
        return null;
    }

    @Override
    public void move(int fromPosition, int toPosition) {
        dataBlockObserver.disableDataChangedNotify();
        CollectionUtils.move(this, fromPosition, toPosition);
        dataBlockObserver.enableDataChangedNotify();
        dataBlockObserver.notifyDataRangeMoved(fromPosition, toPosition);
    }

    @Override
    protected List<T> proxy() {
        updateOldData();
        return oldList;
    }

    private void updateOldData() {
        if (isDirtyData.compareAndSet(true, false)) {
            oldList.clear();
            ForeachUtils.each(this.dataBlocks, oldList::addAll);
        }
    }

    @Override
    public void dirty() {
        isDirtyData.set(true);
        ForeachUtils.each(this.dataBlocks, DataSource::dirty);
    }

}
