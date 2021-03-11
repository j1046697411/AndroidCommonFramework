package org.jzl.android.recyclerview.util.datablock.impl;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.util.datablock.DataBlockObserver;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.OnDataBlockChangedCallback;
import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

class DataBlockObserverImpl<T, D extends DataBlockProvider<T>> implements DataBlockObserver<T, D> {

    private final AtomicInteger semaphore = new AtomicInteger(0);
    private final CopyOnWriteArrayList<OnDataBlockChangedCallback<T, D>> dataBlockChangedCallbacks = new CopyOnWriteArrayList<>();
    private final D dataBlockProvider;

    public DataBlockObserverImpl(@NonNull D dataBlockProvider) {
        this.dataBlockProvider = dataBlockProvider;
    }

    @Override
    public void enableDataChangedNotify() {
        semaphore.decrementAndGet();
    }

    @Override
    public void disableDataChangedNotify() {
        semaphore.incrementAndGet();
    }

    private boolean isEnableDataChangedNotify() {
        return semaphore.get() == 0;
    }

    @Override
    public void addOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, D> dataBlockChangedCallback) {
        if (ObjectUtils.nonNull(dataBlockChangedCallback)) {
            this.dataBlockChangedCallbacks.add(dataBlockChangedCallback);
        }
    }

    @Override
    public void removeOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, D> dataBlockChangedCallback) {
        this.dataBlockChangedCallbacks.remove(dataBlockChangedCallback);
    }

    @Override
    public void notifyDataRangeChanged(int positionStart, int itemCount) {
        dataBlockProvider.dirty();
        if (isEnableDataChangedNotify()) {
            ForeachUtils.each(this.dataBlockChangedCallbacks, target -> target.onItemRangeChanged(dataBlockProvider, positionStart, itemCount));
        }
    }

    @Override
    public void notifyDataRangeInserted(int positionStart, int itemCount) {
        dataBlockProvider.dirty();
        if (isEnableDataChangedNotify()) {
            ForeachUtils.each(this.dataBlockChangedCallbacks, target -> target.onItemRangeInserted(dataBlockProvider, positionStart, itemCount));
        }
    }

    @Override
    public void notifyDataRangeMoved(int fromPosition, int toPosition) {
        dataBlockProvider.dirty();
        if (isEnableDataChangedNotify()) {
            ForeachUtils.each(this.dataBlockChangedCallbacks, target -> target.onItemRangeMoved(dataBlockProvider, fromPosition, toPosition));
        }
    }

    @Override
    public void notifyDataRangeRemoved(int positionStart, int itemCount) {
        dataBlockProvider.dirty();
        if (isEnableDataChangedNotify()) {
            ForeachUtils.each(this.dataBlockChangedCallbacks, target -> target.onItemRangeRemoved(dataBlockProvider, positionStart, itemCount));
        }
    }

    @Override
    public void notifyDataChanged() {
        dataBlockProvider.dirty();
        if (isEnableDataChangedNotify()) {
            ForeachUtils.each(this.dataBlockChangedCallbacks, target -> target.onChanged(dataBlockProvider));
        }
    }

}
