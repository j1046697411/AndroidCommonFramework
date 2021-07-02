package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

import org.jzl.lang.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

class OptionsBuilder<T, VH extends IViewHolder> implements IOptionsBuilder<T, VH> {
    private static final IViewFactoryStoreFactory<Object, IViewHolder> DEFAULT_VIEW_FACTORY_STORE_FACTORY = (options, injects) -> {
        List<IViewFactoryOwner> viewFactoryOwners = new ArrayList<>();
        for (Function<IOptions<Object, IViewHolder>, IViewFactoryOwner> inject : injects) {
            viewFactoryOwners.add(inject.apply(options));
        }
        return new ViewFactoryStore(options, viewFactoryOwners);
    };

    private static final IDataBinderStoreFactory<Object, IViewHolder> DEFAULT_DATA_BINDER_STORE_FACTORY = DataBinderStore::new;

    @NonNull
    final IConfiguration<?, ?> configuration;

    @NonNull
    final IModule<T, VH> module;

    @NonNull
    final IViewHolderFactory<T, VH> viewHolderFactory;
    final List<Function<IOptions<T, VH>, IViewFactoryOwner>> itemViewInjects = new ArrayList<>();
    final List<IDataBinderOwner<T, VH>> dataBinderOwners = new ArrayList<>();
    int priority = 5;
    @SuppressWarnings("unchecked")
    IViewFactoryStoreFactory<T, VH> viewFactoryStoreFactory = (IViewFactoryStoreFactory<T, VH>) DEFAULT_VIEW_FACTORY_STORE_FACTORY;

    @SuppressWarnings("unchecked")
    IDataBinderStoreFactory<T, VH> dataBinderStoreFactory = (IDataBinderStoreFactory<T, VH>) DEFAULT_DATA_BINDER_STORE_FACTORY;

    OptionsBuilder(@NonNull IConfiguration<?, ?> configuration, @NonNull IModule<T, VH> module, @NonNull IViewHolderFactory<T, VH> viewHolderFactory) {
        this.configuration = configuration;
        this.module = module;
        this.viewHolderFactory = viewHolderFactory;
    }

    @Override
    @NonNull
    public IOptions<T, VH> build() {
        return new Options<>(this);
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> setViewFactoryStoreFactory(IViewFactoryStoreFactory<T, VH> viewFactoryStoreFactory) {
        this.viewFactoryStoreFactory = ObjectUtils.get(viewFactoryStoreFactory, this.viewFactoryStoreFactory);
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> createItemView(@NonNull Function<IOptions<T, VH>, IViewFactoryOwner> inject) {
        if (!itemViewInjects.contains(inject)) {
            itemViewInjects.add(inject);
        }
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> setDataBinderStoreFactory(IDataBinderStoreFactory<T, VH> dataBinderStoreFactory) {
        this.dataBinderStoreFactory = ObjectUtils.get(dataBinderStoreFactory, this.dataBinderStoreFactory);
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> dataBinding(@NonNull IDataBinder<T, VH> dataBinder, @NonNull IBindPolicy bindPolicy, int priority) {
        this.dataBinderOwners.add(new DataBinderOwner<>(bindPolicy, dataBinder, priority));
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> setPriority(int priority) {
        this.priority = priority;
        return this;
    }


}
