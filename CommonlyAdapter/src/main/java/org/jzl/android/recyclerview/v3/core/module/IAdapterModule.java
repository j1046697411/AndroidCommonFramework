package org.jzl.android.recyclerview.v3.core.module;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IDataClassifier;
import org.jzl.android.recyclerview.v3.core.IDataProvider;
import org.jzl.android.recyclerview.v3.core.IIdentityProvider;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderFactory;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;

public interface IAdapterModule<T, VH extends IViewHolder> extends IMultipleModule<T, VH>, IDataClassifier<T, VH>, IIdentityProvider<T, VH> {

    default int getItemCount(IConfiguration<T, VH> configuration, IDataProvider<T> dataProvider) {
        return dataProvider.size();
    }

    @Override
    default int getItemViewType(IConfiguration<T, VH> configuration, IDataProvider<T> dataProvider, int position) {
        return configuration.getDataClassifier().getItemViewType(configuration, dataProvider, position);
    }

    @Override
    default long getItemId(IConfiguration<T, VH> configuration, IDataProvider<T> dataProvider, int position) {
        return configuration.getIdentityProvider().getItemId(configuration, dataProvider, position);
    }

    default T getItemData(IConfiguration<T, VH> configuration, IDataProvider<T> dataProvider, int position) {
        return dataProvider.get(position);
    }

    @NonNull
    static <T, VH extends IViewHolder> IAdapterModule<T, VH> of(@NonNull IViewHolderFactory<VH> viewHolderFactory, @NonNull IListenerManager<T, VH> listenerManager) {
        return new MultipleAdapterModule<>(viewHolderFactory, listenerManager);
    }

}
