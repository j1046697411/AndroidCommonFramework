package org.jzl.android.recyclerview.core.item;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.data.Extractable;

public interface ItemDataHolder<T, VH extends RecyclerView.ViewHolder> extends ItemContext, Extractable {

    RecyclerView.Adapter<VH> getAdapter();

    Configuration<T, VH> getConfiguration();

    T getItemData();

}
