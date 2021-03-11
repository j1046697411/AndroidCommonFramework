package org.jzl.android.recyclerview.manager.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface OnDetachedFromRecyclerViewListener<T, VH extends RecyclerView.ViewHolder> {

    void onDetachedFromRecyclerView(@NonNull Configuration<T, VH> configuration, @NonNull RecyclerView recyclerView);

}
