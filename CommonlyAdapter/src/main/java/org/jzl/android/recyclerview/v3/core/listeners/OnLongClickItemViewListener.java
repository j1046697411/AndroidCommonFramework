package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;

public interface OnLongClickItemViewListener<T, VH extends IViewHolder> {

    boolean onLongClickItemView(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner);
}
