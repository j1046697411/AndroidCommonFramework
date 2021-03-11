package org.jzl.android.recyclerview.core.factory;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;

public interface ItemDataHolderFactory<T, VH extends RecyclerView.ViewHolder> {

    ItemDataHolder<T, VH> createItemDataHolder(RecyclerView.Adapter<VH> adapter, Configuration<T, VH> configuration, VH holder);

}
