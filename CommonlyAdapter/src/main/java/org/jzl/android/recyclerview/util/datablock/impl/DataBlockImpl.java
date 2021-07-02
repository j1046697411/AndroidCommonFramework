package org.jzl.android.recyclerview.util.datablock.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.PositionType;
import org.jzl.lang.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataBlockImpl<T> extends AbstractDataSource<T> implements DataBlock<T> {

    private final int dataBlockId;
    private final PositionType positionType;
    private final AtomicBoolean isDirtyData = new AtomicBoolean(true);
    private int sortOrder = IdHelper.ID.incrementAndGet();
    private DataBlockProvider<T> dataBlockProvider;
    private int startPosition;
    private final ArrayList<T> data = new ArrayList<T>() {
        @Override
        protected void removeRange(int fromIndex, int toIndex) {
            super.removeRange(fromIndex, toIndex);
            if (ObjectUtils.nonNull(dataBlockProvider)) {
                dataBlockProvider.getDataBlockObserver().notifyDataRangeRemoved(fromIndex + getStartPosition(), toIndex - fromIndex);
            }
        }
    };

    public DataBlockImpl(int dataBlockId, PositionType positionType) {
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
        if (ObjectUtils.nonNull(dataBlockProvider) && isDirtyData.compareAndSet(true, false)) {
            startPosition = DataBlockHelper.getStartPosition(dataBlockProvider, this);
        }
        return startPosition;
    }

    @Override
    public void bindDataBlockProvider(DataBlockProvider<T> dataBlockProvider) {
        this.dataBlockProvider = dataBlockProvider;
        dirty();
    }

    @Override
    public void unbindDataBlockProvider() {
        if (ObjectUtils.nonNull(dataBlockProvider)) {
            this.dataBlockProvider = null;
        }
    }

    @Override
    protected List<T> proxy() {
        return data;
    }

    @Override
    public boolean add(T t) {
        boolean is = super.add(t);
        if (is && ObjectUtils.nonNull(dataBlockProvider)) {
            dataBlockProvider.getDataBlockObserver().notifyDataRangeInserted(size() - 1 + getStartPosition(), 1);
        }
        return is;
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
        if (ObjectUtils.nonNull(dataBlockProvider)) {
            dataBlockProvider.getDataBlockObserver().notifyDataRangeInserted(index + getStartPosition(), 1);
        }
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        boolean is = super.addAll(index, c);
        if (is && ObjectUtils.nonNull(dataBlockProvider)) {
            dataBlockProvider.getDataBlockObserver().notifyDataRangeInserted(index + getStartPosition(), c.size());
        }
        return is;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        int size = size();
        boolean is = super.addAll(c);
        if (is && ObjectUtils.nonNull(dataBlockProvider)) {
            dataBlockProvider.getDataBlockObserver().notifyDataRangeInserted(size + getStartPosition(), c.size());
        }
        return is;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return ObjectUtils.nonNull(remove(indexOf(o)));
    }

    @Override
    public T remove(int index) {
        T obj = super.remove(index);
        if (ObjectUtils.nonNull(obj) && ObjectUtils.nonNull(dataBlockProvider)) {
            dataBlockProvider.getDataBlockObserver().notifyDataRangeRemoved(index + getStartPosition(), 1);
        }
        return obj;
    }

    @Override
    public T set(int index, T element) {
        T obj = super.set(index, element);
        if (ObjectUtils.nonNull(dataBlockProvider)) {
            dataBlockProvider.getDataBlockObserver().notifyDataRangeChanged(index + getStartPosition(), 1);
        }
        return obj;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }
        int oldSize = size();
        super.clear();
        if (ObjectUtils.nonNull(dataBlockProvider)) {
            dataBlockProvider.getDataBlockObserver().notifyDataRangeRemoved(getStartPosition(), oldSize);
        }
    }

    @Override
    public void move(int fromPosition, int toPosition) {
        super.move(fromPosition, toPosition);
        if (ObjectUtils.nonNull(dataBlockProvider)) {
            int startPosition = getStartPosition();
            dataBlockProvider.getDataBlockObserver().notifyDataRangeMoved(fromPosition + startPosition, toPosition + startPosition);
        }
    }

    @Override
    public void dirty() {
        isDirtyData.set(true);
    }

}
