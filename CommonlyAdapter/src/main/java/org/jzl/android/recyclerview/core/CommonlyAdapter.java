package org.jzl.android.recyclerview.core;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.R;
import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.core.data.Identifiable;
import org.jzl.android.recyclerview.core.factory.ItemViewFactory;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;
import org.jzl.android.recyclerview.core.item.ItemDataHolderUpdatable;
import org.jzl.android.recyclerview.core.vh.ViewHolderFactory;
import org.jzl.android.recyclerview.manager.layout.EmptyLayoutManager;
import org.jzl.android.recyclerview.manager.listener.ListenerManager;
import org.jzl.android.recyclerview.util.Logger;
import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.List;

public class CommonlyAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private static final Logger log = Logger.logger(CommonlyAdapter.class);

    private final Configuration<T, VH> configuration;
    private final ViewHolderFactory<VH> viewHolderFactory;
    private final DataProvider<T, VH> dataProvider;
    private final LayoutInflater layoutInflater;
    private final ListenerManager<T, VH> listenerManager;
    private final EmptyLayoutManager<T, VH> emptyLayoutManager;

    public CommonlyAdapter(Configuration<T, VH> configuration) {
        this.configuration = configuration;
        this.dataProvider = configuration.getDataProvider();
        this.viewHolderFactory = configuration.getViewHolderFactory();
        layoutInflater = configuration.getLayoutInflater();
        this.listenerManager = configuration.getListenerManager();
        this.emptyLayoutManager = configuration.getEmptyLayoutManager();
    }

    private void applyComponents(RecyclerView recyclerView) {
        ForeachUtils.each(configuration.getComponents(), target -> target.initialise(recyclerView, configuration));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        applyComponents(recyclerView);
        listenerManager.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        listenerManager.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull VH holder) {
        return listenerManager.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        listenerManager.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        listenerManager.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        listenerManager.onViewRecycled(holder);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewFactory itemViewFactory = matchItemViewFactory(viewType);
        ObjectUtils.requireNonNull(itemViewFactory, "itemViewFactory is null viewType(" + viewType + ")");
        VH holder = viewHolderFactory.createViewHolder(itemViewFactory.createItemView(layoutInflater, parent, viewType), viewType);
        ItemDataHolder<T, VH> dataHolder = createItemDataHolder(holder);
        listenerManager.onCreatedViewHolder(dataHolder, holder);
        return holder;
    }

    private ItemViewFactory matchItemViewFactory(int viewType) {
        return configuration.matchItemViewFactory(viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        T data = getData(position);

        ItemDataHolder<T, VH> itemDataHolder = getItemDataHolder(holder);
        if (itemDataHolder instanceof ItemDataHolderUpdatable) {
            ((ItemDataHolderUpdatable<T, VH>) itemDataHolder).update(data, payloads);
        }
        configuration.getItemDataUpdateHelper().update(itemDataHolder);
        configuration.getViewBindHelper().onBindViewHolder(itemDataHolder, holder);
    }

    protected ItemDataHolder<T, VH> createItemDataHolder(VH holder) {
        ItemDataHolder<T, VH> dataHolder = configuration.getItemDataHolderFactory().createItemDataHolder(this, configuration, holder);
        holder.itemView.setTag(R.id.tag_item_data, dataHolder);
        return dataHolder;
    }

    @SuppressWarnings("unchecked")
    private ItemDataHolder<T, VH> getItemDataHolder(VH holder) {
        return (ItemDataHolder<T, VH>) holder.itemView.getTag(R.id.tag_item_data);
    }

    @Override
    public int getItemCount() {
        if (isEmptyEnable()) {
            return 1;
        }
        return dataProvider.size();
    }

    private T getData(int position) {
        if (isEmptyEnable()) {
            return emptyLayoutManager.getEmptyData();
        }
        long s = System.currentTimeMillis();
        try {
            return dataProvider.get(position);
        } finally {
            log.i("getData -> " + (System.currentTimeMillis() - s));
        }

    }

    @Override
    public long getItemId(int position) {
        if (isEmptyEnable()) {
            return getDataId(emptyLayoutManager.getEmptyData());
        }
        return getDataId(dataProvider.get(position));
    }

    private long getDataId(T data) {
        if (data instanceof Identifiable) {
            return ((Identifiable) data).getId();
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmptyEnable()) {
            return emptyLayoutManager.getItemType();
        }
        return configuration.getDataClassifier().getDataType(dataProvider, position);
    }

    private boolean isEmptyEnable() {
        return dataProvider.isEmpty() && emptyLayoutManager.isEnable();
    }

}
