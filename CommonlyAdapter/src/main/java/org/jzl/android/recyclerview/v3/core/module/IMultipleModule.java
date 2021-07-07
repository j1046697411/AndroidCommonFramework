package org.jzl.android.recyclerview.v3.core.module;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IOptionsBuilder;
import org.jzl.android.recyclerview.v3.core.IViewFactoryOwner;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.lang.fun.Function;

public interface IMultipleModule<T, VH extends IViewHolder> extends IModule<T, VH> {

    <T1, VH1 extends VH> void registered(@NonNull IModule<T1, VH1> module, @NonNull Function<T, T1> mapper);

    default <VH1 extends VH> void registered(@NonNull IModule<T, VH1> module) {
        registered(module, target -> target);
    }

    void registered(@NonNull IMultipleModule.IRegistrar<T, VH> registrar);

    interface IRegistrar<T, VH extends IViewHolder> {

        void registered(@NonNull IConfiguration<?, ?> configuration, @NonNull IOptionsBuilder<T, VH> optionsBuilder, @NonNull IDataGetter<T> dataGetter);
    }
}
