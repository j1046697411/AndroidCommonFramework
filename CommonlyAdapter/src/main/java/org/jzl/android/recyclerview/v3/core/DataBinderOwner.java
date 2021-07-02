package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

class DataBinderOwner<T, VH extends IViewHolder> implements IDataBinderOwner<T, VH> {

    private final IBindPolicy bindPolicy;
    private final IDataBinder<T, VH> dataBinder;
    private final int priority;

    DataBinderOwner(IBindPolicy bindPolicy, IDataBinder<T, VH> dataBinder, int priority) {
        this.bindPolicy = bindPolicy;
        this.dataBinder = dataBinder;
        this.priority = priority;
    }

    @NonNull
    @Override
    public IBindPolicy getBindPolicy() {
        return bindPolicy;
    }

    @NonNull
    @Override
    public IDataBinder<T, VH> getDataBinder() {
        return dataBinder;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
