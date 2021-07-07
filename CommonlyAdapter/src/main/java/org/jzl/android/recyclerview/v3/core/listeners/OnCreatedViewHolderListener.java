package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;

@FunctionalInterface
public interface OnCreatedViewHolderListener<T, VH extends IViewHolder> {

    void onCreatedViewHolder(IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner);

}
