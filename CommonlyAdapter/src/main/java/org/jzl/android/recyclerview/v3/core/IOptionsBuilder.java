package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

public interface IOptionsBuilder<T, VH extends IViewHolder> {

    @NonNull
    IOptions<T, VH> build();

    @NonNull
    IOptionsBuilder<T, VH> setViewFactoryStoreFactory(IViewFactoryStoreFactory<T, VH> viewFactoryStoreFactory);

    @NonNull
    default IOptionsBuilder<T, VH> createItemView(@LayoutRes int layoutResId, int... itemViewTypes) {
        return createItemView(IViewFactory.of(layoutResId), itemViewTypes);
    }

    @NonNull
    default IOptionsBuilder<T, VH> createItemView(@NonNull IViewFactory viewFactory, int... itemViewTypes) {
        return createItemView(viewFactory, IMatchPolicy.ofItemTypes(itemViewTypes), 10);
    }

    @NonNull
    default IOptionsBuilder<T, VH> createItemView(@NonNull IViewFactory viewFactory, @NonNull IMatchPolicy matchPolicy, int priority) {
        return createItemView(options -> new ViewFactoryOwner(options, viewFactory, matchPolicy, priority));
    }

    @NonNull
    default IOptionsBuilder<T, VH> createItemView(IViewFactoryOwner viewFactoryOwner) {
        return createItemView(options -> viewFactoryOwner);
    }

    @NonNull
    IOptionsBuilder<T, VH> createItemView(@NonNull Function<IOptions<T, VH>, IViewFactoryOwner> consumer);

    @NonNull
    IOptionsBuilder<T, VH> setDataBinderStoreFactory(IDataBinderStoreFactory<T, VH> dataBinderStoreFactory);

    @NonNull
    IOptionsBuilder<T, VH> dataBinding(@NonNull IDataBinder<T, VH> dataBinder, @NonNull IBindPolicy bindPolicy, int priority);

    @NonNull
    IOptionsBuilder<T, VH> setPriority(int priority);
}
