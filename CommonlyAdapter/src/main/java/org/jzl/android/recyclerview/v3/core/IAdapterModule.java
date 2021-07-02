package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

import java.util.List;

public interface IAdapterModule<T, VH extends IViewHolder> extends IModule<T, VH>, IDataClassifier<T, VH>, IIdentityProvider<T, VH> {

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

    @SuppressWarnings("unchecked")
    default <T1, VH1 extends VH> void registered(@NonNull IModule<T1, VH1> module, @NonNull Function<T, T1> mapper) {
        registered((configuration, optionsBuilder) -> {
            IOptions<T1, VH1> options = module.setup(configuration);
            List<IViewFactoryOwner> viewFactoryOwners = options.getUnmodifiableViewFactoryOwners();
            for (IViewFactoryOwner viewFactoryOwner : viewFactoryOwners) {
                optionsBuilder.createItemView(viewFactoryOwner);
            }
            optionsBuilder.dataBinding((context, viewHolder, data) -> options.getDataBinder().binding(context, (VH1) viewHolder, mapper.apply(data)), context -> context.getOptions() == options, options.getPriority());
        });
    }

    default <VH1 extends VH> void registered(@NonNull IModule<T, VH1> module) {
        registered(module, target -> target);
    }

    void registered(@NonNull IRegistrar<T, VH> registrar);

    interface IRegistrar<T, VH extends IViewHolder> {

        void registered(@NonNull IConfiguration<?, ?> configuration, @NonNull IOptionsBuilder<T, VH> optionsBuilder);
    }

}
