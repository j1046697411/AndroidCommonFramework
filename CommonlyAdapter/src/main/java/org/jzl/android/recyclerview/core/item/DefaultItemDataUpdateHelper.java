package org.jzl.android.recyclerview.core.item;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.lang.util.ForeachUtils;

public class DefaultItemDataUpdateHelper<T, VH extends RecyclerView.ViewHolder> implements ItemDataUpdateHelper<T, VH> {

    private Configuration<T, VH> configuration;

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        this.configuration = configuration;
    }

    @Override
    public void update(ItemDataHolder<T, VH> dataHolder) {
        ForeachUtils.each(configuration.getItemDataUpdates(), target -> target.update(dataHolder));
    }

}
