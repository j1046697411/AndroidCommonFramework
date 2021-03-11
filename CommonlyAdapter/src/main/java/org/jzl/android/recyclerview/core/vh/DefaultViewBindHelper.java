package org.jzl.android.recyclerview.core.vh;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;
import org.jzl.lang.util.holder.BinaryHolder;

public class DefaultViewBindHelper<T, VH extends RecyclerView.ViewHolder> implements ViewBindHelper<T, VH> {

    protected RecyclerView recyclerView;
    protected Configuration<T, VH> configuration;

    @Override
    public final void onBindViewHolder(ItemDataHolder<T, VH> dataHolder, VH viewHolder) {
        beforeBindViewHolder(dataHolder, viewHolder);
        onBindViewHolder(configuration, dataHolder, viewHolder);
        afterBindViewHolder(dataHolder, viewHolder);
    }

    protected void beforeBindViewHolder(ItemDataHolder<T, VH> dataHolder, VH viewHolder) {
        if (viewHolder instanceof DataBinderCallback) {
            ((DataBinderCallback) viewHolder).beforeBindViewHolder();
        }
    }

    protected void afterBindViewHolder(ItemDataHolder<T, VH> dataHolder, VH viewHolder) {
        if (viewHolder instanceof DataBinderCallback) {
            ((DataBinderCallback) viewHolder).afterBindViewHolder();
        }
    }

    protected void onBindViewHolder(Configuration<T, VH> configuration, ItemDataHolder<T, VH> dataHolder, VH viewHolder) {
        for (BinaryHolder<DataBindingMatchPolicy, DataBinder<T, VH>> holder : configuration.getDataBinders()) {
            if (holder.one.match(dataHolder)) {
                holder.two.binding(configuration, viewHolder, dataHolder.getItemData());
            }
        }
    }

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        this.recyclerView = recyclerView;
        this.configuration = configuration;
    }

    @Override
    public void onDispose() {
    }
}
