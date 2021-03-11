package org.jzl.android.recyclerview.manager.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;
import org.jzl.android.recyclerview.manager.AbstractManager;
import org.jzl.lang.util.ForeachUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultListenerManager<T, VH extends RecyclerView.ViewHolder> extends AbstractManager<T, VH> implements ListenerManager<T, VH> {

    private Configuration<T, VH> configuration;

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        this.configuration = configuration;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        ForeachUtils.each(configuration.getOnAttachedToRecyclerViewListeners(), target -> target.onAttachedToRecyclerView(configuration, recyclerView));
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        ForeachUtils.each(configuration.getOnDetachedFromRecyclerViewListeners(), target -> target.onDetachedFromRecyclerView(configuration, recyclerView));
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull VH holder) {
        return false;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        ForeachUtils.each(configuration.getOnViewAttachedToWindowListeners(), target -> target.onViewAttachedToWindow(configuration, holder));
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        ForeachUtils.each(configuration.getOnViewDetachedFromWindowListeners(), target -> target.onViewDetachedFromWindow(configuration, holder));
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {

        ForeachUtils.each(this.configuration.getOnViewRecycledListeners(), target -> target.onViewRecycled(configuration, holder));
    }

    @Override
    public void onCreatedViewHolder(ItemDataHolder<T, VH> dataHolder, VH holder) {
        ForeachUtils.each(configuration.getOnCreatedViewHolderListeners(), target -> target.onCreatedViewHolder(configuration, dataHolder, holder));
        holder.itemView.setOnClickListener(v -> onItemClick(dataHolder, holder));
        holder.itemView.setOnLongClickListener(v -> onItemLongClick(dataHolder, holder));
    }

    protected void onItemClick(ItemDataHolder<T, VH> dataHolder, VH viewHolder) {
        ForeachUtils.each(configuration.getOnItemClickListeners(), target -> {
            if (target.one.match(dataHolder)) {
                target.two.onItemClick(configuration, viewHolder, dataHolder.getItemData());
            }
        });
    }

    protected boolean onItemLongClick(ItemDataHolder<T, VH> dataHolder, VH viewHolder) {
        AtomicBoolean isLongClick = new AtomicBoolean(false);
        ForeachUtils.each(configuration.getOnItemLongClickListeners(), target -> {
            if (target.one.match(dataHolder)) {
                target.two.onItemLongClick(configuration, viewHolder, dataHolder.getItemData());
                isLongClick.set(true);
            }
        });
        return isLongClick.get();
    }
}
