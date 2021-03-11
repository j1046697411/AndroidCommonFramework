package org.jzl.android.recyclerview.manager.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;

public interface ListenerManager<T, VH extends RecyclerView.ViewHolder> extends Component<T, VH> {

    @Override
    void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration);

    void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView);

    void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView);

    boolean onFailedToRecycleView(@NonNull VH holder);

    void onViewAttachedToWindow(@NonNull VH holder);

    void onViewDetachedFromWindow(@NonNull VH holder);

    void onViewRecycled(@NonNull VH holder);

    //自定义事件

    void onCreatedViewHolder(ItemDataHolder<T, VH> dataHolder, VH holder);

}
