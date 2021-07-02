package org.jzl.android.recyclerview.v3.core;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public interface IOptions<T, VH extends IViewHolder> extends IViewHolderOwner<T, VH>,
        IViewFactoryStore, IDataBinderStore<T, VH> {

    @NonNull
    IConfiguration<?, ?> getConfiguration();

    @NonNull
    IModule<T, VH> getModule();

    @NonNull
    @Override
    IViewHolderFactory<T, VH> getViewHolderFactory();

    @NonNull
    @Override
    IMatchPolicy getMatchPolicy();

    @NonNull
    @Override
    IViewFactory getViewFactory();

    @NonNull
    @Override
    default IOptions<?, ?> getOptions() {
        return this;
    }

    @Override
    IViewFactoryOwner get(int itemViewType);

    @Override
    IDataBinderOwner<T, VH> get(IContext context);

    @NonNull
    @Override
    IBindPolicy getBindPolicy();

    @NonNull
    @Override
    IDataBinder<T, VH> getDataBinder();

    @NonNull
    ModuleAdapter.ModuleAdapterViewHolder<T, VH> createViewHolder(@NonNull IConfiguration<?, ?> configuration, @NonNull ViewGroup parent, int viewType);

    int getPriority();

    @NonNull
    VH createViewHolder(@NonNull View itemView, int itemViewType);

}
