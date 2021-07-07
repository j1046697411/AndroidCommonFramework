package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;
import org.jzl.android.recyclerview.v3.core.module.IModule;

import java.util.List;

class Options<T, VH extends IViewHolder> implements IOptions<T, VH> {

    private final IConfiguration<?, ?> configuration;
    private final IModule<T, VH> module;
    private final IViewHolderFactory<VH> viewHolderFactory;
    private final IViewFactoryStore<VH> viewFactoryStore;
    private final IDataBinderStore<T, VH> dataBinderStore;
    private final IDataGetter<T> dataGetter;

    private final int priority;
    private final IListenerManager<T, VH> listenerManager;

    Options(OptionsBuilder<T, VH> builder) {
        this.configuration = builder.configuration;
        this.module = builder.module;
        this.viewHolderFactory = builder.viewHolderFactory;
        this.priority = builder.priority;
        this.dataGetter = builder.dataGetter;
        this.listenerManager = builder.listenerManager;
        this.viewFactoryStore = builder.viewFactoryStoreFactory.createViewFactoryStore(this, builder.itemViewInjects);
        this.dataBinderStore = builder.dataBinderStoreFactory.createDataBinderStore(this, builder.dataBinderOwners);
    }

    public static <T, VH extends IViewHolder> IOptionsBuilder<T, VH> builder(@NonNull IConfiguration<?, ?> configuration, @NonNull IModule<T, VH> module, @NonNull IViewHolderFactory<VH> viewHolderFactory, @NonNull IDataGetter<T> dataGetter, @NonNull IListenerManager<T, VH> listenerManager) {
        return new OptionsBuilder<>(configuration, module, viewHolderFactory, dataGetter, listenerManager);
    }

    @NonNull
    @Override
    public IConfiguration<?, ?> getConfiguration() {
        return configuration;
    }

    @NonNull
    @Override
    public IModule<T, VH> getModule() {
        return module;
    }

    @NonNull
    @Override
    public IViewHolderFactory<VH> getViewHolderFactory() {
        return viewHolderFactory;
    }

    @NonNull
    @Override
    public IMatchPolicy getMatchPolicy() {
        return viewFactoryStore.getMatchPolicy();
    }

    @NonNull
    @Override
    public IOptions<?, VH> getOptions() {
        return viewFactoryStore.getOptions();
    }

    @NonNull
    @Override
    public IViewFactory getViewFactory() {
        return viewFactoryStore.getViewFactory();
    }

    @Override
    public IViewFactoryOwner<VH> get(int itemViewType) {
        return viewFactoryStore.get(itemViewType);
    }

    @NonNull
    @Override
    public List<IViewFactoryOwner<VH>> getUnmodifiableViewFactoryOwners() {
        return viewFactoryStore.getUnmodifiableViewFactoryOwners();
    }

    @Override
    public IDataBinderOwner<T, VH> get(IContext context) {
        return dataBinderStore.get(context);
    }

    @NonNull
    @Override
    public IBindPolicy getBindPolicy() {
        return dataBinderStore.getBindPolicy();
    }

    @NonNull
    @Override
    public IDataBinder<T, VH> getDataBinder() {
        return dataBinderStore.getDataBinder();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @NonNull
    @Override
    public IListenerManager<T, VH> getListenerManager() {
        return listenerManager;
    }

    @Override
    public final void notifyCreatedViewHolder(@NonNull IViewHolderOwner<VH> viewHolderOwner) {
        listenerManager.notifyCreatedViewHolder(this, viewHolderOwner);
    }

    @Override
    public void notifyViewAttachedToWindow(@NonNull IViewHolderOwner<VH> viewHolderOwner) {
        listenerManager.notifyViewAttachedToWindow(this, viewHolderOwner);
    }

    @Override
    public void notifyViewDetachedFromWindow(@NonNull IViewHolderOwner<VH> viewHolderOwner) {
        listenerManager.notifyViewDetachedFromWindow(this, viewHolderOwner);
    }

    @Override
    public void notifyAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        listenerManager.notifyAttachedToRecyclerView(recyclerView, this);
    }

    @Override
    public void notifyDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        listenerManager.notifyDetachedFromRecyclerView(recyclerView, this);
    }

    @Override
    public void notifyViewRecycled(@NonNull IViewHolderOwner<VH> viewHolderOwner) {
        listenerManager.notifyViewRecycled(this, viewHolderOwner);
    }

    @NonNull
    @Override
    public IDataGetter<T> getDataGetter() {
        return dataGetter;
    }

}
