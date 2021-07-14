package org.jzl.android.recyclerview.v3.core.module;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderFactory;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;

public interface IAdapterModule<T, VH extends IViewHolder> extends IMultipleModule<T, VH> {

    default int getItemCount(IConfiguration<T, VH> configuration) {
        return configuration.getDataProvider().size();
    }

    default int getItemViewType(IConfiguration<T, VH> configuration, int position) {
        return configuration.getDataClassifier().getItemViewType(configuration, configuration.getDataGetter().getData(position), position);
    }

    default long getItemId(IConfiguration<T, VH> configuration, int position) {
        return configuration.getIdentityProvider().getItemId(configuration, configuration.getDataGetter().getData(position), position);
    }

    default T getItemData(@NonNull IConfiguration<T, VH> configuration, int position) {
        return configuration.getDataProvider().get(position);
    }

    @NonNull
    static <T, VH extends IViewHolder> IAdapterModule<T, VH> of(@NonNull IViewHolderFactory<VH> viewHolderFactory, @NonNull IListenerManager<T, VH> listenerManager) {
        return new MultipleAdapterModule<>(viewHolderFactory, listenerManager);
    }

}
