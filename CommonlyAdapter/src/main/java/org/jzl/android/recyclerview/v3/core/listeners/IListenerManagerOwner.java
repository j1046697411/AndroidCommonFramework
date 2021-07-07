package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface IListenerManagerOwner<T, VH extends IViewHolder> {

    @NonNull
    IListenerManager<T, VH> getListenerManager();

}
