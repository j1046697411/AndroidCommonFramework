package org.jzl.android.recyclerview.v3.core.module;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface IAdapterModuleProxy<T, VH extends IViewHolder> {

    @NonNull
    IAdapterModule<T, VH> proxy(@NonNull IConfiguration<T, VH> configuration, @NonNull IAdapterModule<T, VH> adapterModule);

    void addProxy(@NonNull IProxy<T, VH> proxy);

    @FunctionalInterface
    interface IProxy<T, VH extends IViewHolder> extends Comparable<IProxy<?, ?>> {

        @NonNull
        IAdapterModule<T, VH> proxy(@NonNull IConfiguration<T, VH> configuration, @NonNull IAdapterModule<T, VH> target);

        default int getPriority() {
            return 5;
        }

        @Override
        default int compareTo(IProxy<?, ?> o) {
            return Integer.compare(getPriority(), o.getPriority());
        }
    }

}
