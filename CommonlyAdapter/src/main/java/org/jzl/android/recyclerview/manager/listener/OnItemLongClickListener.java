package org.jzl.android.recyclerview.manager.listener;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface OnItemLongClickListener<T, VH extends RecyclerView.ViewHolder> {

    void onItemLongClick(Configuration<T, VH> configuration, VH viewHolder, T data);

}
