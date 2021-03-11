package org.jzl.android.recyclerview.core;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.ConfigurationBuilder;

public interface Plugin<T, VH extends RecyclerView.ViewHolder> {

    void setup(ConfigurationBuilder<T, VH> builder);

}
