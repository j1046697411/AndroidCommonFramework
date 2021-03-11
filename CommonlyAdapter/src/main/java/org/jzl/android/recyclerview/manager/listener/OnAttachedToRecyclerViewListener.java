package org.jzl.android.recyclerview.manager.listener;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface OnAttachedToRecyclerViewListener<T, VH extends RecyclerView.ViewHolder> {

    void onAttachedToRecyclerView(Configuration<T, VH> configuration, RecyclerView recyclerView);

}
