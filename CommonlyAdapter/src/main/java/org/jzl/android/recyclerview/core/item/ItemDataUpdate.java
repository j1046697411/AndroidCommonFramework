package org.jzl.android.recyclerview.core.item;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemDataUpdate<T, VH extends RecyclerView.ViewHolder> {

    void update(ItemDataHolder<T, VH> dataHolder);

}
