package org.jzl.android.recyclerview.manager;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.configuration.ConfigurationBuilder;

public abstract class AbstractManager<T, VH extends RecyclerView.ViewHolder> implements Manager<T, VH> {

    @Override
    public void setup(ConfigurationBuilder<T, VH> builder) {
        builder.component(this);
    }

    @Override
    public abstract void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration);
}
