package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManagerBuilder;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManagerOwner;
import org.jzl.android.recyclerview.v3.core.listeners.OnClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnLongClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.module.IModule;

public interface IOptions<T, VH extends IViewHolder> extends IViewHolderFactoryOwner<VH>,
        IViewFactoryStore<VH>, IDataBinderStore<T, VH>, IDataGetterOwner<T> , IListenerManagerOwner<T, VH> {

    @NonNull
    IConfiguration<?, ?> getConfiguration();

    @NonNull
    IModule<T, VH> getModule();

    @NonNull
    @Override
    IViewHolderFactory<VH> getViewHolderFactory();

    @NonNull
    @Override
    IMatchPolicy getMatchPolicy();

    @NonNull
    @Override
    IViewFactory getViewFactory();

    @NonNull
    @Override
    IOptions<?, VH> getOptions();

    @Override
    IViewFactoryOwner<VH> get(int itemViewType);

    @Override
    IDataBinderOwner<T, VH> get(IContext context);

    @NonNull
    @Override
    IBindPolicy getBindPolicy();

    @NonNull
    @Override
    IDataBinder<T, VH> getDataBinder();

    int getPriority();

    @NonNull
    @Override
    IListenerManager<T, VH> getListenerManager();

    void notifyCreatedViewHolder(@NonNull IViewHolderOwner<VH> viewHolderOwner, int viewType);

    void notifyViewAttachedToWindow(@NonNull IViewHolderOwner<VH> viewHolderOwner);

    void notifyViewDetachedFromWindow(@NonNull IViewHolderOwner<VH> viewHolderOwner);

    void notifyAttachedToRecyclerView(@NonNull RecyclerView recyclerView);

    void notifyDetachedFromRecyclerView(@NonNull RecyclerView recyclerView);

    void notifyViewRecycled(@NonNull IViewHolderOwner<VH> viewHolderOwner);
}
