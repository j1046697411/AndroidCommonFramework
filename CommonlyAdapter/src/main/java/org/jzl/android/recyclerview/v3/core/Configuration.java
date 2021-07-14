package org.jzl.android.recyclerview.v3.core;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.components.IComponentManager;
import org.jzl.android.recyclerview.v3.core.layout.IRecyclerViewLayoutManager;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.OnAttachedToRecyclerViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnDetachedFromRecyclerViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnLongClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewAttachedToWindowListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewDetachedFromWindowListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewRecycledListener;
import org.jzl.android.recyclerview.v3.core.module.IAdapterModule;
import org.jzl.lang.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Configuration<T, VH extends IViewHolder> implements IConfiguration<T, VH>, IDataGetter<T> {

    private final IAdapterModule<T, VH> adapterModule;
    private final List<T> dataProvider;
    private final IDataClassifier<T, VH> dataClassifier;
    private final IIdentityProvider<T, VH> identityProvider;
    private final LayoutInflater layoutInflater;
    private final ModuleAdapter<T, VH> adapter;
    private final IOptions<T, VH> options;
    private final IListenerManager<T, VH> listenerManager;
    private final IComponentManager<T, VH> componentManager;
    private final IRecyclerViewLayoutManager<T, VH> recyclerViewLayoutManager;
    private final IAdapterObservable<T, VH> adapterObservable;

    private final Handler mainHandler;
    private final ExecutorService executorService;

    Configuration(ConfigurationBuilder<T, VH> builder, LayoutInflater layoutInflater) {
        this.dataClassifier = builder.dataClassifier;
        this.identityProvider = builder.identityProvider;
        this.dataProvider = builder.dataProvider;
        this.layoutInflater = layoutInflater;
        this.listenerManager = builder.listenerManager;
        this.componentManager = builder.componentManager;
        this.recyclerViewLayoutManager = builder.recyclerViewLayoutManager;

        this.mainHandler = ObjectUtils.get(builder.mainHandler, () -> new Handler(Looper.getMainLooper()));
        this.executorService = ObjectUtils.get(builder.executorService, Executors::newSingleThreadExecutor);

        this.adapterModule = builder.adapterModuleProxy.proxy(this, builder.adapterModule);
        this.adapter = new ModuleAdapter<>(this);
        this.options = adapter.setup(this, this);
        this.adapterObservable = new AdapterObservable<>(this, this.adapter);
    }

    public static <T, VH extends IViewHolder> ConfigurationBuilder<T, VH> builder(@NonNull IViewHolderFactory<VH> viewHolderFactory) {
        return new ConfigurationBuilder<>(viewHolderFactory);
    }

    @NonNull
    @Override
    public IAdapterModule<T, VH> getAdapterModule() {
        return adapterModule;
    }

    @NonNull
    @Override
    public List<T> getDataProvider() {
        return dataProvider;
    }

    @NonNull
    @Override
    public IDataClassifier<T, VH> getDataClassifier() {
        return dataClassifier;
    }

    @NonNull
    @Override
    public IIdentityProvider<T, VH> getIdentityProvider() {
        return identityProvider;
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    @NonNull
    @Override
    public IDataGetter<T> getDataGetter() {
        return this;
    }

    @Override
    public T getData(int position) {
        return adapterModule.getItemData(this, position);
    }

    @NonNull
    @Override
    public IOptions<T, VH> getOptions() {
        return options;
    }

    @NonNull
    @Override
    public RecyclerView.Adapter<?> getAdapter() {
        return adapter;
    }

    @NonNull
    @Override
    public IComponentManager<T, VH> getComponentManager() {
        return componentManager;
    }

    @NonNull
    @Override
    public IRecyclerViewLayoutManager<T, VH> getRecyclerViewLayoutManager() {
        return recyclerViewLayoutManager;
    }

    @NonNull
    @Override
    public IAdapterObservable<T, VH> getAdapterObservable() {
        return adapterObservable;
    }

    @Override
    public boolean isDataEmpty() {
        return dataProvider.isEmpty();
    }

    @NonNull
    @Override
    public Handler getMainHandler() {
        return mainHandler;
    }

    @NonNull
    @Override
    public ExecutorService getExecutorService() {
        return executorService;
    }

    @NonNull
    @Override
    public IConfiguration<T, VH> addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, @NonNull IMatchPolicy matchPolicy) {
        listenerManager.addOnCreatedViewHolderListener(createdViewHolderListener, matchPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfiguration<T, VH> addOnClickItemViewListener(@NonNull OnClickItemViewListener<T, VH> clickItemViewListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addOnClickItemViewListener(clickItemViewListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfiguration<T, VH> addOnLongClickItemViewListener(@NonNull OnLongClickItemViewListener<T, VH> longClickItemViewListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addOnLongClickItemViewListener(longClickItemViewListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfiguration<T, VH> addViewAttachedToWindowListener(@NonNull OnViewAttachedToWindowListener<T, VH> viewAttachedToWindowListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewAttachedToWindowListener(viewAttachedToWindowListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfiguration<T, VH> addViewDetachedFromWindowListener(@NonNull OnViewDetachedFromWindowListener<T, VH> viewDetachedFromWindowListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewDetachedFromWindowListener(viewDetachedFromWindowListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfiguration<T, VH> addAttachedToRecyclerViewListener(@NonNull OnAttachedToRecyclerViewListener<T, VH> attachedToRecyclerViewListener) {
        listenerManager.addAttachedToRecyclerViewListener(attachedToRecyclerViewListener);
        return this;
    }

    @NonNull
    @Override
    public IConfiguration<T, VH> addDetachedFromRecyclerViewListener(@NonNull OnDetachedFromRecyclerViewListener<T, VH> detachedFromRecyclerViewListener) {
        listenerManager.addDetachedFromRecyclerViewListener(detachedFromRecyclerViewListener);
        return this;
    }

    @Override
    public IConfiguration<T, VH> addViewRecycledListener(@NonNull OnViewRecycledListener<T, VH> viewRecycledListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewRecycledListener(viewRecycledListener, bindPolicy);
        return this;
    }
}
