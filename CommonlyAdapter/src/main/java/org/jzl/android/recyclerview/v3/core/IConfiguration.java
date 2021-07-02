package org.jzl.android.recyclerview.v3.core;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;

public interface IConfiguration<T, VH extends IViewHolder> {

    @NonNull
    static <T, VH extends IViewHolder> IConfigurationBuilder<T, VH> builder() {
        return Configuration.builder();
    }

    @NonNull
    default <T1, VH1 extends IViewHolder> IOptionsBuilder<T1, VH1> options(@NonNull IModule<T1, VH1> module, @NonNull IViewHolderFactory<T1, VH1> viewHolderFactory) {
        return Options.builder(this, module, viewHolderFactory);
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

    interface IConfigurationBuilder<T, VH extends IViewHolder> {

        @NonNull
        IConfigurationBuilder<T, VH> setDataProvider(IDataProvider<T> dataProvider);

        @NonNull
        IConfigurationBuilder<T, VH> setDataClassifier(IDataClassifier<T, VH> dataClassifier);

        @NonNull
        IConfigurationBuilder<T, VH> setIdentityProvider(IIdentityProvider<T, VH> identityProvider);

        @NonNull
        <T1, VH1 extends VH> IConfigurationBuilder<T, VH> registered(@NonNull IModule<T1, VH1> module, @NonNull Function<T, T1> mapper);

        @NonNull
        default <VH1 extends VH> IConfigurationBuilder<T, VH> registered(@NonNull IModule<T, VH1> module) {
            return registered(module, target -> target);
        }

        @NonNull
        IConfiguration<T, VH> build(@NonNull RecyclerView recyclerView);
    }
}
