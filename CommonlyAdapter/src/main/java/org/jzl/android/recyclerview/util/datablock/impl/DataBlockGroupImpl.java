package org.jzl.android.recyclerview.util.datablock.impl;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.util.Logger;
import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockFactory;
import org.jzl.android.recyclerview.util.datablock.DataBlockGroup;
import org.jzl.android.recyclerview.util.datablock.DataBlockObserver;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.OnDataBlockChangedCallback;
import org.jzl.android.recyclerview.util.datablock.PositionType;
import org.jzl.lang.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataBlockGroupImpl<T> extends AbstractDataSource<T> implements DataBlockGroup<T> {

    private final DataBlockProvider<T> dataBlockProvider;
    private final int dataBlockId;
    private final PositionType positionType;
    private final AtomicBoolean isDirtyData = new AtomicBoolean(true);
    private int sortOrder = IdHelper.ID.incrementAndGet();
    private DataBlockProvider<T> parentDataBlockProvider;
    private int startPosition;

    public DataBlockGroupImpl(int dataBlockId, PositionType positionType, int defaultDataBlockId, DataBlockFactory<T> dataBlockFactory) {
        dataBlockProvider = new DataBlockProviderImpl<>(defaultDataBlockId, dataBlockFactory);
        this.dataBlockProvider.addOnDataBlockChangedCallback(new OnDataBlockChangedCallbackImpl());
        this.dataBlockId = dataBlockId;
        this.positionType = positionType;
    }

    @Override
    public int getSortOrder() {
        return sortOrder;
    }

    @Override
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int getDataBlockId() {
        return dataBlockId;
    }

    @Override
    public PositionType getPositionType() {
        return positionType;
    }

    @Override
    public int getStartPosition() {
        if (ObjectUtils.nonNull(parentDataBlockProvider) && isDirtyData.compareAndSet(true, false)) {
            startPosition = DataBlockHelper.getStartPosition(parentDataBlockProvider, this);
        }
        return startPosition;
    }

    @Override
    public void bindDataBlockProvider(DataBlockProvider<T> dataBlockProvider) {
        this.parentDataBlockProvider = dataBlockProvider;
        dirty();
    }

    @Override
    public void unbindDataBlockProvider() {
        if (ObjectUtils.nonNull(parentDataBlockProvider)) {
            parentDataBlockProvider = null;
        }
    }

    @NonNull
    @Override
    public Collection<DataBlock<T>> getDataBlocks() {
        return dataBlockProvider.getDataBlocks();
    }

    @NonNull
    @Override
    public DataBlockObserver<T, DataBlockProvider<T>> getDataBlockObserver() {
        return dataBlockProvider.getDataBlockObserver();
    }

    @Override
    public void addOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, DataBlockProvider<T>> dataBlockChangedCallback) {
        dataBlockProvider.addOnDataBlockChangedCallback(dataBlockChangedCallback);
    }

    @Override
    public void removeOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, DataBlockProvider<T>> dataBlockChangedCallback) {
        dataBlockProvider.removeOnDataBlockChangedCallback(dataBlockChangedCallback);
    }

    @Override
    public void addDataBlock(@NonNull DataBlock<T> dataBlock) {
        dataBlockProvider.addDataBlock(dataBlock);
    }

    @Override
    public void addDataBlockAll(@NonNull Collection<DataBlock<T>> dataBlocks) {
        dataBlockProvider.addDataBlockAll(dataBlocks);
    }

    @Override
    public void removeDataBlock(@NonNull DataBlock<T> dataBlock) {
        dataBlockProvider.removeDataBlock(dataBlock);
    }

    @Override
    public void removeDataBlockAll(@NonNull Collection<DataBlock<T>> dataBlocks) {
        dataBlockProvider.removeDataBlockAll(dataBlocks);
    }

    @Override
    public DataBlock<T> dataBlock(PositionType positionType, int dataBlockId) {
        return dataBlockProvider.dataBlock(positionType, dataBlockId);
    }

    @Override
    public DataBlock<T> defaultDataBlock() {
        return dataBlockProvider.defaultDataBlock();
    }

    @Override
    public DataBlock<T> lastDataBlock() {
        return dataBlockProvider.lastDataBlock();
    }

    @Override
    public DataBlock<T> lastContentDataBlock() {
        return dataBlockProvider.lastContentDataBlock();
    }

    @Override
    public Set<DataBlock<T>> findDataBlockByPositionType(PositionType positionType) {
        return dataBlockProvider.findDataBlockByPositionType(positionType);
    }

    @Override
    public DataBlock<T> findDataBlockByPositionTypeAndDataBlockId(PositionType positionType, int dataBlockId) {
        return dataBlockProvider.findDataBlockByPositionTypeAndDataBlockId(positionType, dataBlockId);
    }

    @Override
    public DataBlock<T> findDataBlockByPosition(int position) {
        return dataBlockProvider.findDataBlockByPosition(position);
    }

    @Override
    protected List<T> proxy() {
        return dataBlockProvider;
    }

    @Override
    public void dirty() {
        isDirtyData.set(true);
    }

    private class OnDataBlockChangedCallbackImpl implements OnDataBlockChangedCallback<T, DataBlockProvider<T>> {

        private final Logger log = Logger.logger(OnDataBlockChangedCallback.class);

        @Override
        public void onChanged(DataBlockProvider<T> sender) {
            log.i("onChanged");
            dirty();
            if (ObjectUtils.nonNull(parentDataBlockProvider)) {
                parentDataBlockProvider.getDataBlockObserver().notifyDataChanged();
            }
        }

        @Override
        public void onItemRangeChanged(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            log.i("onItemRangeChanged -> " + positionStart + ',' + itemCount);
            dirty();
            if (ObjectUtils.nonNull(parentDataBlockProvider)) {
                parentDataBlockProvider.getDataBlockObserver().notifyDataRangeChanged(positionStart + getStartPosition(), itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            log.i("onItemRangeInserted -> " + positionStart + "," + itemCount);
            dirty();
            if (ObjectUtils.nonNull(parentDataBlockProvider)) {
                parentDataBlockProvider.getDataBlockObserver().notifyDataRangeInserted(positionStart + getStartPosition(), itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(DataBlockProvider<T> sender, int fromPosition, int toPosition) {
            log.i("onItemRangeMoved -> " + fromPosition + "," + toPosition);
            int startPosition = getStartPosition();
            dirty();
            if (ObjectUtils.nonNull(parentDataBlockProvider)) {
                parentDataBlockProvider.getDataBlockObserver().notifyDataRangeMoved(fromPosition + startPosition, toPosition + startPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            log.i("onItemRangeRemoved -> " + positionStart + "," + itemCount);
            dirty();
            if (ObjectUtils.nonNull(parentDataBlockProvider)) {
                parentDataBlockProvider.getDataBlockObserver().notifyDataRangeRemoved(positionStart + getStartPosition(), itemCount);
            }
        }
    }
}
