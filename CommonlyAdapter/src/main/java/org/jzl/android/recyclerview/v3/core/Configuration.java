package org.jzl.android.recyclerview.v3.core;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

class Configuration<T, VH extends IViewHolder> implements IConfiguration<T, VH>, IDataGetter<T> {

    private final IAdapterModule<T, VH> adapterModule;
    private final IDataProvider<T> dataProvider;
    private final IDataClassifier<T, VH> dataClassifier;
    private final IIdentityProvider<T, VH> identityProvider;
    private final LayoutInflater layoutInflater;
    private final ModuleAdapter<T, VH> adapter;
    private final IOptions<T, VH> options;
    private final IListenerManager<T, VH> listenerManager;

    Configuration(ConfigurationBuilder<T, VH> builder, LayoutInflater layoutInflater) {
        this.adapterModule = builder.adapterModule;
        this.dataClassifier = builder.dataClassifier;
        this.identityProvider = builder.identityProvider;
        this.dataProvider = builder.dataProvider;
        this.layoutInflater = layoutInflater;
        this.listenerManager = builder.listenerManager;
        this.adapter = new ModuleAdapter<>(this);
        this.options = adapter.setup(this, this);
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
    public IDataProvider<T> getDataProvider() {
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
        return adapterModule.getItemData(this, dataProvider, position);
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
    public IConfiguration<T, VH> addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addOnCreatedViewHolderListener(createdViewHolderListener, bindPolicy);
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
