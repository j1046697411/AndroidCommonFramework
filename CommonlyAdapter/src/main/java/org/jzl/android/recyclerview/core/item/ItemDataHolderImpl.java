package org.jzl.android.recyclerview.core.item;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.data.Extractable;
import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.lang.util.ObjectUtils;

import java.util.List;

public class ItemDataHolderImpl<T, VH extends RecyclerView.ViewHolder> implements ItemDataHolder<T, VH>, ItemDataHolderUpdatable<T, VH>, Extractable {

    protected final RecyclerView.Adapter<VH> adapter;
    protected final Configuration<T, VH> configuration;
    protected final VH holder;

    private T data;
    private List<Object> payloads;

    public ItemDataHolderImpl(RecyclerView.Adapter<VH> adapter, Configuration<T, VH> configuration, VH holder) {
        this.adapter = adapter;
        this.configuration = configuration;
        this.holder = holder;
    }

    @Override
    public RecyclerView.Adapter<VH> getAdapter() {
        return adapter;
    }

    @Override
    public Configuration<T, VH> getConfiguration() {
        return configuration;
    }

    @Override
    public T getItemData() {
        return data;
    }

    @Override
    public int getAdapterPosition() {
        return holder.getAdapterPosition();
    }

    @Override
    public int getItemViewType() {
        return holder.getItemViewType();
    }

    @Override
    public long getItemId() {
        return holder.getItemId();
    }

    @Override
    public List<Object> getPayloads() {
        return payloads;
    }

    @Override
    public void update(T data, List<Object> payloads) {
        this.data = data;
        this.payloads = payloads;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E getExtra(int key) {
        return (E) holder.itemView.getTag(key);
    }

    @Override

    public <E> E getExtra(int key, E defValue) {
        return ObjectUtils.get(getExtra(key), defValue);
    }

    @Override
    public void putExtra(int key, Object value) {
        holder.itemView.setTag(key, value);
    }

    @Override
    public boolean hasExtra(int key) {
        return ObjectUtils.nonNull(holder.itemView.getTag(key));
    }

    @Override
    public void removeExtra(int key) {
        holder.itemView.setTag(key, null);
    }
}
