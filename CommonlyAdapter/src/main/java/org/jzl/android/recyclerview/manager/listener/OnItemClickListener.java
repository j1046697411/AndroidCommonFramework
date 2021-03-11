package org.jzl.android.recyclerview.manager.listener;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface OnItemClickListener<T, VH extends RecyclerView.ViewHolder> {

    void onItemClick(Configuration<T, VH> configuration, VH viewHolder, T data);

}
