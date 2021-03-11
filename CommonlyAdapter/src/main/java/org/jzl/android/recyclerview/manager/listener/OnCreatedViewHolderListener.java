package org.jzl.android.recyclerview.manager.listener;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;

public interface OnCreatedViewHolderListener<T, VH extends RecyclerView.ViewHolder> {

    void onCreatedViewHolder(Configuration<T, VH> configuration, ItemDataHolder<T, VH> dataHolder, VH holder);

}
