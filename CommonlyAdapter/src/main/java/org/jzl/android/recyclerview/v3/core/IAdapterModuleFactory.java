package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IAdapterModuleFactory<T, VH extends IViewHolder> {

    @NonNull
    IAdapterModule<T, VH> createAdapterModule(@NonNull IViewHolderFactory<T, VH> viewHolderFactory);

}
