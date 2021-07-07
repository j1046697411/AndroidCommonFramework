package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.listeners.IListenerManagerBuilder;
import org.jzl.android.recyclerview.v3.core.listeners.OnClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnLongClickItemViewListener;
import org.jzl.lang.fun.Function;

public interface IOptionsBuilder<T, VH extends IViewHolder> extends IListenerManagerBuilder<T, VH, IOptionsBuilder<T, VH>> {

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
        return createItemView(options -> new ViewFactoryOwner<>(options, viewFactory, matchPolicy, priority));
    }

    @NonNull
    default IOptionsBuilder<T, VH> createItemView(IViewFactoryOwner<VH> viewFactoryOwner) {
        return createItemView(options -> viewFactoryOwner);
    }

    @NonNull
    IOptionsBuilder<T, VH> createItemView(@NonNull Function<IOptions<T, VH>, IViewFactoryOwner<VH>> consumer);

    @NonNull
    IOptionsBuilder<T, VH> setDataBinderStoreFactory(IDataBinderStoreFactory<T, VH> dataBinderStoreFactory);

    @NonNull
    IOptionsBuilder<T, VH> dataBinding(@NonNull IDataBinder<T, VH> dataBinder, @NonNull IBindPolicy bindPolicy, int priority);

    @NonNull
    default IOptionsBuilder<T, VH> dataBinding(IDataBinder<T, VH> dataBinder, @NonNull IBindPolicy bindPolicy) {
        return dataBinding(dataBinder, bindPolicy, 10);
    }

    @NonNull
    default IOptionsBuilder<T, VH> dataBindingByItemViewTypes(@NonNull IDataBinder<T, VH> dataBinder, int... itemViewTypes) {
        return dataBinding(dataBinder, IBindPolicy.ofItemViewTypes(itemViewTypes));
    }

    @NonNull
    default IOptionsBuilder<T, VH> dataBindingByPayloads(@NonNull IDataBinder<T, VH> dataBinder, Object... payloads) {
        return dataBinding(dataBinder, IBindPolicy.ofPayloads(payloads));
    }

    default IOptionsBuilder<T, VH> dataBindingByPayloadsOrNotIncludedPayloads(@NonNull IDataBinder<T, VH> dataBinder, Object... payloads) {
        return dataBinding(dataBinder, IBindPolicy.ofPayloads(payloads).or(IBindPolicy.BIND_POLICY_NOT_INCLUDED_PAYLOADS));
    }

    @NonNull
    IOptionsBuilder<T, VH> setPriority(int priority);

    @NonNull
    @Override
    IOptionsBuilder<T, VH> addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    @Override
    IOptionsBuilder<T, VH> addOnClickItemViewListener(@NonNull OnClickItemViewListener<T, VH> clickItemViewListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    @Override
    IOptionsBuilder<T, VH> addOnLongClickItemViewListener(@NonNull OnLongClickItemViewListener<T, VH> longClickItemViewListener, @NonNull IBindPolicy bindPolicy);
}
