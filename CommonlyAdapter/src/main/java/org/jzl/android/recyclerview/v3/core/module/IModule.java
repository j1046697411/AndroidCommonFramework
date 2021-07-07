package org.jzl.android.recyclerview.v3.core.module;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface IModule<T, VH extends IViewHolder> {

    @NonNull
    IOptions<T, VH> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<T> dataGetter);

}
