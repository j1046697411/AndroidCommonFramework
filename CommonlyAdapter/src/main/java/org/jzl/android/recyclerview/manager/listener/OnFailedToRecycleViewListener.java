package org.jzl.android.recyclerview.manager.listener;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface OnFailedToRecycleViewListener<T, VH extends RecyclerView.ViewHolder> {

    boolean onFailedToRecycleView(Configuration<T, VH> configuration, VH holder);

}
