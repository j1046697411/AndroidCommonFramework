package org.jzl.android.recyclerview.core.vh;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface DataBinder<T, VH extends RecyclerView.ViewHolder> {

    void binding(Configuration<T, VH> configuration, VH holder, T data);

}