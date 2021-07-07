package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface OnAttachedToRecyclerViewListener<T, VH extends IViewHolder> {

    void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView, @NonNull IOptions<T, VH> options);

}
