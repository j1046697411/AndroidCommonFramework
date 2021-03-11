package org.jzl.android.recyclerview.core.configuration;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerViewConfiguration<T, VH extends RecyclerView.ViewHolder> extends Configuration<T, VH> {

    RecyclerView getRecyclerView();



}
