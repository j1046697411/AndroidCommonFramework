package org.jzl.android.recyclerview.v3.core;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

class Options<T, VH extends IViewHolder> implements IOptions<T, VH> {

    private final IConfiguration<?, ?> configuration;
    private final IModule<T, VH> module;
    private final IViewHolderFactory<T, VH> viewHolderFactory;
    private final IViewFactoryStore viewFactoryStore;
    private final IDataBinderStore<T, VH> dataBinderStore;

    private final int priority;

    Options(OptionsBuilder<T, VH> builder) {
        this.configuration = builder.configuration;
        this.module = builder.module;
        this.viewHolderFactory = builder.viewHolderFactory;
        this.priority = builder.priority;

        this.viewFactoryStore = builder.viewFactoryStoreFactory.createViewFactoryStore(this, builder.itemViewInjects);
        this.dataBinderStore = builder.dataBinderStoreFactory.createDataBinderStore(this, builder.dataBinderOwners);
    }

    public static <T, VH extends IViewHolder> IOptionsBuilder<T, VH> builder(@NonNull IConfiguration<?, ?> configuration, @NonNull IModule<T, VH> module, @NonNull IViewHolderFactory<T, VH> viewHolderFactory) {
        return new OptionsBuilder<>(configuration, module, viewHolderFactory);
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
    public IViewHolderFactory<T, VH> getViewHolderFactory() {
        return viewHolderFactory;
    }

    @NonNull
    @Override
    public IMatchPolicy getMatchPolicy() {
        return viewFactoryStore.getMatchPolicy();
    }

    @NonNull
    @Override
    public IViewFactory getViewFactory() {
        return viewFactoryStore.getViewFactory();
    }

    @Override
    public IViewFactoryOwner get(int itemViewType) {
        return viewFactoryStore.get(itemViewType);
    }

    @NonNull
    @Override
    public List<IViewFactoryOwner> getUnmodifiableViewFactoryOwners() {
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

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public ModuleAdapter.ModuleAdapterViewHolder<T, VH> createViewHolder(@NonNull IConfiguration<?, ?> configuration, @NonNull ViewGroup parent, int viewType) {
        IViewFactoryOwner viewFactoryOwner = get(viewType);
        IOptions<?, ?> options = viewFactoryOwner.getOptions();
        IViewFactory viewFactory = viewFactoryOwner.getViewFactory();
        View itemView = viewFactory.create(configuration.getLayoutInflater(), parent, viewType);
        IViewHolder viewHolder = options.createViewHolder(itemView, viewType);
        return new ModuleAdapter.ModuleAdapterViewHolder<>(options, itemView, (VH) viewHolder);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @NonNull
    @Override
    public VH createViewHolder(@NonNull View itemView, int itemViewType) {
        return viewHolderFactory.createViewHolder(this, itemView, itemViewType);
    }

}
