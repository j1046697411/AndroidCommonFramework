package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;

public interface OnViewAttachedToWindowListener<T, VH extends IViewHolder> {

    void onViewAttachedToWindow(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> owner);

}
