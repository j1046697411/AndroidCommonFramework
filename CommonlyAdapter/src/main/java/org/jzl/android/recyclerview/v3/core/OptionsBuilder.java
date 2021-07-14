package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.OnAttachedToRecyclerViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnDetachedFromRecyclerViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnLongClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewAttachedToWindowListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewDetachedFromWindowListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewRecycledListener;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.lang.fun.Function;
import org.jzl.lang.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

class OptionsBuilder<T, VH extends IViewHolder> implements IOptionsBuilder<T, VH> {
    private static final IViewFactoryStoreFactory<Object, IViewHolder> DEFAULT_VIEW_FACTORY_STORE_FACTORY = (options, injects) -> {
        List<IViewFactoryOwner<IViewHolder>> viewFactoryOwners = new ArrayList<>();
        for (Function<IOptions<Object, IViewHolder>, IViewFactoryOwner<IViewHolder>> inject : injects) {
            viewFactoryOwners.add(inject.apply(options));
        }
        return new ViewFactoryStore<>(options, viewFactoryOwners);
    };

    private static final IDataBinderStoreFactory<Object, IViewHolder> DEFAULT_DATA_BINDER_STORE_FACTORY = DataBinderStore::new;

    @NonNull
    final IConfiguration<?, ?> configuration;

    @NonNull
    final IModule<T, VH> module;
    final IDataGetter<T> dataGetter;

    @NonNull
    final IViewHolderFactory<VH> viewHolderFactory;
    final List<Function<IOptions<T, VH>, IViewFactoryOwner<VH>>> itemViewInjects = new ArrayList<>();
    final List<IDataBinderOwner<T, VH>> dataBinderOwners = new ArrayList<>();
    int priority = 5;
    @SuppressWarnings("unchecked")
    IViewFactoryStoreFactory<T, VH> viewFactoryStoreFactory = (IViewFactoryStoreFactory<T, VH>) DEFAULT_VIEW_FACTORY_STORE_FACTORY;

    @SuppressWarnings("unchecked")
    IDataBinderStoreFactory<T, VH> dataBinderStoreFactory = (IDataBinderStoreFactory<T, VH>) DEFAULT_DATA_BINDER_STORE_FACTORY;

    final IListenerManager<T, VH> listenerManager;

    OptionsBuilder(@NonNull IConfiguration<?, ?> configuration, @NonNull IModule<T, VH> module, @NonNull IViewHolderFactory<VH> viewHolderFactory, @NonNull IDataGetter<T> dataGetter, @NonNull IListenerManager<T, VH> listenerManager) {
        this.configuration = configuration;
        this.module = module;
        this.viewHolderFactory = viewHolderFactory;
        this.dataGetter = dataGetter;
        this.listenerManager = listenerManager;
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
    public IOptionsBuilder<T, VH> createItemView(@NonNull Function<IOptions<T, VH>, IViewFactoryOwner<VH>> inject) {
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

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, @NonNull IMatchPolicy matchPolicy) {
        listenerManager.addOnCreatedViewHolderListener(createdViewHolderListener, matchPolicy);
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> addOnClickItemViewListener(@NonNull OnClickItemViewListener<T, VH> clickItemViewListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addOnClickItemViewListener(clickItemViewListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> addOnLongClickItemViewListener(@NonNull OnLongClickItemViewListener<T, VH> longClickItemViewListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addOnLongClickItemViewListener(longClickItemViewListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> addViewAttachedToWindowListener(@NonNull OnViewAttachedToWindowListener<T, VH> viewAttachedToWindowListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewAttachedToWindowListener(viewAttachedToWindowListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> addViewDetachedFromWindowListener(@NonNull OnViewDetachedFromWindowListener<T, VH> viewDetachedFromWindowListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewDetachedFromWindowListener(viewDetachedFromWindowListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> addAttachedToRecyclerViewListener(@NonNull OnAttachedToRecyclerViewListener<T, VH> attachedToRecyclerViewListener) {
        listenerManager.addAttachedToRecyclerViewListener(attachedToRecyclerViewListener);
        return this;
    }

    @NonNull
    @Override
    public IOptionsBuilder<T, VH> addDetachedFromRecyclerViewListener(@NonNull OnDetachedFromRecyclerViewListener<T, VH> detachedFromRecyclerViewListener) {
        listenerManager.addDetachedFromRecyclerViewListener(detachedFromRecyclerViewListener);
        return this;
    }

    @Override
    public IOptionsBuilder<T, VH> addViewRecycledListener(@NonNull OnViewRecycledListener<T, VH> viewRecycledListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewRecycledListener(viewRecycledListener, bindPolicy);
        return this;
    }
}
