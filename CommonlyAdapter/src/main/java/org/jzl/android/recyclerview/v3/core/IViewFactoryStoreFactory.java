package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import org.jzl.lang.fun.Function;

import java.util.List;

public interface IViewFactoryStoreFactory<T, VH extends IViewHolder> {

    @NonNull
    IViewFactoryStore<VH> createViewFactoryStore(@NonNull IOptions<T, VH> options, @NonNull List<Function<IOptions<T, VH>, IViewFactoryOwner<VH>>> injects);

}
