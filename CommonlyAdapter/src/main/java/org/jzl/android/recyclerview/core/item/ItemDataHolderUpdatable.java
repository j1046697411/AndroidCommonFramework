package org.jzl.android.recyclerview.core.item;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface ItemDataHolderUpdatable<T, VH extends RecyclerView.ViewHolder> extends ItemDataHolder<T, VH> {

    void update(T data, List<Object> payloads);

}
