package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface OnDetachedFromRecyclerViewListener<T, VH extends IViewHolder> {

    void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView, @NonNull IOptions<T, VH> options);

}
