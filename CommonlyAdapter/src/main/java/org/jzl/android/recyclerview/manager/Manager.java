package org.jzl.android.recyclerview.manager;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.configuration.ConfigurationBuilder;
import org.jzl.android.recyclerview.core.Plugin;

public interface Manager<T, VH extends RecyclerView.ViewHolder> extends Component<T, VH>, Plugin<T, VH> {

    @Override
    void setup(ConfigurationBuilder<T, VH> builder);

    @Override
    void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration);
}
