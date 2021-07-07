package org.jzl.android.recyclerview.v3.core;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManagerBuilder;
import org.jzl.android.recyclerview.v3.core.listeners.ListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.OnClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnLongClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.module.IAdapterModule;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.lang.fun.Function;

public interface IConfiguration<T, VH extends IViewHolder> extends IDataGetterOwner<T> , IListenerManagerBuilder<T, VH, IConfiguration<T, VH>> {

    @NonNull
    static <T, VH extends IViewHolder> IConfigurationBuilder<T, VH> builder(@NonNull IViewHolderFactory<VH> viewHolderFactory) {
        return Configuration.builder(viewHolderFactory);
    }

    @NonNull
    default <T1, VH1 extends IViewHolder> IOptionsBuilder<T1, VH1> options(@NonNull IModule<T1, VH1> module, @NonNull IViewHolderFactory<VH1> viewHolderFactory, @NonNull IDataGetter<T1> dataGetter, @NonNull IListenerManager<T1, VH1> listenerManager) {
        return Options.builder(this, module, viewHolderFactory, dataGetter, listenerManager);
    }

    @NonNull
    default <T1, VH1 extends IViewHolder> IOptionsBuilder<T1, VH1> options(@NonNull IModule<T1, VH1> module, @NonNull IViewHolderFactory<VH1> viewHolderFactory, @NonNull IDataGetter<T1> dataGetter){
        return options(module, viewHolderFactory, dataGetter, new ListenerManager<>());
    }


    @NonNull
    IAdapterModule<T, VH> getAdapterModule();

    @NonNull
    IDataProvider<T> getDataProvider();

    @NonNull
    IDataClassifier<T, VH> getDataClassifier();

    @NonNull
    IIdentityProvider<T, VH> getIdentityProvider();

    @NonNull
    LayoutInflater getLayoutInflater();

    @NonNull
    @Override
    IDataGetter<T> getDataGetter();

    @NonNull
    IOptions<T, VH> getOptions();

    @NonNull
    RecyclerView.Adapter<?> getAdapter();

}
