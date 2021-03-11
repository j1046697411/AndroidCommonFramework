package org.jzl.android.recyclerview.core.factory;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface AdapterFactory<T, VH extends RecyclerView.ViewHolder> {

    RecyclerView.Adapter<VH> createAdapter(Configuration<T, VH> configuration);

}
