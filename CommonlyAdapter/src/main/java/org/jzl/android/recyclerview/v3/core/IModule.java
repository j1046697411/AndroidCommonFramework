package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IModule<T, VH extends IViewHolder> {

    @NonNull
    IOptions<T, VH> setup(@NonNull IConfiguration<?, ?> configuration);

}
