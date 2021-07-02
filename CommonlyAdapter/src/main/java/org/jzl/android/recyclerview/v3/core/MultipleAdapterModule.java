package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MultipleAdapterModule<T, VH extends IViewHolder> implements IAdapterModule<T, VH> {

    private final List<IRegistrar<T, VH>> registrars = new ArrayList<>();

    @NonNull
    @Override
    public IOptions<T, VH> setup(@NonNull IConfiguration<?, ?> configuration) {
        IOptionsBuilder<T, VH> optionsBuilder = configuration
                .options(this, (options, itemView, itemViewType) -> {
                    return null;
                });
        for (IRegistrar<T, VH> registrar : registrars) {
            registrar.registered(configuration, optionsBuilder);
        }
        return optionsBuilder.build();
    }

    @Override
    public void registered(@NonNull IRegistrar<T, VH> registrar) {
        if (!registrars.contains(registrar)) {
            this.registrars.add(registrar);
        }

    }
}
