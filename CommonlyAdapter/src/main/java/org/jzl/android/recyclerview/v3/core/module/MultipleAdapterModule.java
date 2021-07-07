package org.jzl.android.recyclerview.v3.core.module;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderFactory;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;

class MultipleAdapterModule<T, VH extends IViewHolder> extends MultipleModule<T, VH> implements IAdapterModule<T, VH> {
    MultipleAdapterModule(@NonNull IViewHolderFactory<VH> viewHolderFactory, @NonNull IListenerManager<T, VH> listenerManager) {
        super(viewHolderFactory, listenerManager);
    }
}
