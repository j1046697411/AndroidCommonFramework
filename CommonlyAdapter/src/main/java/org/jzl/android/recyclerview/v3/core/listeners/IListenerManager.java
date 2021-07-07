package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.IBindPolicy;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;

public interface IListenerManager<T, VH extends IViewHolder> extends IListenerManagerBuilder<T, VH, IListenerManager<T, VH>> {

    void notifyCreatedViewHolder(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner);

    void notifyViewAttachedToWindow(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner);

    void notifyViewDetachedFromWindow(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner);

    void notifyAttachedToRecyclerView(@NonNull RecyclerView recyclerView, @NonNull IOptions<T, VH> options);

    void notifyDetachedFromRecyclerView(@NonNull RecyclerView recyclerView, @NonNull IOptions<T, VH> options);

    void notifyViewRecycled(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner);

    @NonNull
    @Override
    IListenerManager<T, VH> addOnLongClickItemViewListener(@NonNull OnLongClickItemViewListener<T, VH> longClickItemViewListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    @Override
    IListenerManager<T, VH> addOnClickItemViewListener(@NonNull OnClickItemViewListener<T, VH> clickItemViewListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    @Override
    IListenerManager<T, VH> addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, @NonNull IBindPolicy bindPolicy);
}
