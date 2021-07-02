package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IDataBinderOwner<T, VH extends IViewHolder> {

    @NonNull
    IBindPolicy getBindPolicy();

    @NonNull
    IDataBinder<T, VH> getDataBinder();

    int getPriority();

}
