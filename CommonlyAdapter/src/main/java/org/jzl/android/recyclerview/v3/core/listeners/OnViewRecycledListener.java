package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;

public interface OnViewRecycledListener<T, VH extends IViewHolder> {

    void onViewRecycled(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner);

}
