package org.jzl.android.recyclerview.core;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface Component<T, VH extends RecyclerView.ViewHolder> {

    void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration);

}
