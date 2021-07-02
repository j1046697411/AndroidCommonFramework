package org.jzl.android.recyclerview.v3.core;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.model.Identifiable;
import org.jzl.lang.util.ObjectUtils;

public class Configuration<T, VH extends IViewHolder> implements IConfiguration<T, VH> {

    private final IAdapterModule<T, VH> adapterModule;
    private final IDataProvider<T> dataProvider;
    private final IDataClassifier<T, VH> dataClassifier;
    private final IIdentityProvider<T, VH> identityProvider;
    private final LayoutInflater layoutInflater;

    Configuration(ConfigurationBuilder<T, VH> builder, LayoutInflater layoutInflater) {
        this.adapterModule = builder.adapterModule;
        this.dataClassifier = builder.dataClassifier;
        this.identityProvider = builder.identityProvider;
        this.dataProvider = builder.dataProvider;
        this.layoutInflater = layoutInflater;
    }

    public static <T, VH extends IViewHolder> ConfigurationBuilder<T, VH> builder() {
        return new ConfigurationBuilder<>();
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

    public static class ConfigurationBuilder<T, VH extends IViewHolder> implements IConfiguration.IConfigurationBuilder<T, VH> {

        private final IAdapterModule<T, VH> adapterModule = new MultipleAdapterModule<>();
        private IDataProvider<T> dataProvider;
        private IDataClassifier<T, VH> dataClassifier;
        private IIdentityProvider<T, VH> identityProvider = (configuration, dataProvider, position) -> {
            T data = dataProvider.get(position);
            if (data instanceof Identifiable) {
                return ((Identifiable) data).getId();
            }
            return RecyclerView.NO_ID;
        };

        @NonNull
        @Override
        public IConfigurationBuilder<T, VH> setDataProvider(IDataProvider<T> dataProvider) {
            this.dataProvider = dataProvider;
            return this;
        }

        @NonNull
        @Override
        public IConfigurationBuilder<T, VH> setDataClassifier(IDataClassifier<T, VH> dataClassifier) {
            this.dataClassifier = ObjectUtils.get(dataClassifier, this.dataClassifier);
            return this;
        }

        @NonNull
        @Override
        public IConfigurationBuilder<T, VH> setIdentityProvider(IIdentityProvider<T, VH> identityProvider) {
            this.identityProvider = ObjectUtils.get(identityProvider, this.identityProvider);
            return this;
        }

        @NonNull
        public <T1, VH1 extends VH> IConfigurationBuilder<T, VH> registered(@NonNull IModule<T1, VH1> module, @NonNull Function<T, T1> mapper) {
            adapterModule.registered(module, mapper);
            return this;
        }

        @NonNull
        @Override
        public IConfiguration<T, VH> build(@NonNull RecyclerView recyclerView) {
            Configuration<T, VH> configuration = new Configuration<>(this, LayoutInflater.from(recyclerView.getContext()));
            ModuleAdapter<T, VH> adapter = new ModuleAdapter<>(configuration);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
            return configuration;
        }

    }
}
