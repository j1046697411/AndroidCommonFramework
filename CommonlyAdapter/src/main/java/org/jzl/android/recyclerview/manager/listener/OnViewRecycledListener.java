package org.jzl.android.recyclerview.manager.listener;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface OnViewRecycledListener<T, VH extends RecyclerView.ViewHolder> {
    void onViewRecycled(Configuration<T, VH> configuration, VH holder);
}
