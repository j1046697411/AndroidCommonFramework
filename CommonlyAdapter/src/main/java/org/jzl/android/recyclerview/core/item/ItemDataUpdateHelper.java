package org.jzl.android.recyclerview.core.item;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface ItemDataUpdateHelper<T, VH extends RecyclerView.ViewHolder> extends Component<T, VH> {

    @Override
    void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration);

    void update(ItemDataHolder<T, VH> dataHolder);

}
