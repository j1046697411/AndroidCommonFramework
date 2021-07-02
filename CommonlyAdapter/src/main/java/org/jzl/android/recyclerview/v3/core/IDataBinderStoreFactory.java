package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import java.util.List;

public interface IDataBinderStoreFactory<T, VH extends IViewHolder> {
    IDataBinderStore<T, VH> createDataBinderStore(@NonNull IOptions<T, VH> options, @NonNull List<IDataBinderOwner<T, VH>> dataBinderOwners);
}
