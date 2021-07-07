package org.jzl.android.recyclerview.v3.core;

import android.view.LayoutInflater;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.listeners.IListenerManagerBuilder;
import org.jzl.android.recyclerview.v3.core.listeners.OnClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnLongClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.lang.fun.Consumer;
import org.jzl.lang.fun.Function;

public interface IConfigurationBuilder<T, VH extends IViewHolder> extends IListenerManagerBuilder<T, VH, IConfigurationBuilder<T, VH>> {

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
    default IConfigurationBuilder<T, VH> createItemView(@LayoutRes int layoutResId, int... itemViewTypes) {
        return createItemView(IViewFactory.of(layoutResId), itemViewTypes);
    }

    @NonNull
    default IConfigurationBuilder<T, VH> createItemView(@NonNull IViewFactory viewFactory, int... itemViewTypes) {
        return createItemView(viewFactory, IMatchPolicy.ofItemTypes(itemViewTypes), 10);
    }

    @NonNull
    default IConfigurationBuilder<T, VH> createItemView(@NonNull IViewFactory viewFactory, @NonNull IMatchPolicy matchPolicy, int priority) {
        return createItemView(options -> new ViewFactoryOwner<>(options, viewFactory, matchPolicy, priority));
    }

    @NonNull
    default IConfigurationBuilder<T, VH> createItemView(IViewFactoryOwner<VH> viewFactoryOwner) {
        return createItemView(options -> viewFactoryOwner);
    }

    @NonNull
    IConfigurationBuilder<T, VH> createItemView(@NonNull Function<IOptions<T, VH>, IViewFactoryOwner<VH>> consumer);

    @NonNull
    IConfigurationBuilder<T, VH> dataBinding(@NonNull IDataBinder<T, VH> dataBinder, @NonNull IBindPolicy bindPolicy, int priority);

    @NonNull
    default IConfigurationBuilder<T, VH> dataBinding(IDataBinder<T, VH> dataBinder, @NonNull IBindPolicy bindPolicy) {
        return dataBinding(dataBinder, bindPolicy, 10);
    }

    @NonNull
    default IConfigurationBuilder<T, VH> dataBindingByItemViewTypes(@NonNull IDataBinder<T, VH> dataBinder, int... itemViewTypes) {
        return dataBinding(dataBinder, IBindPolicy.ofItemViewTypes(itemViewTypes));
    }

    @NonNull
    default IConfigurationBuilder<T, VH> dataBindingByPayloads(@NonNull IDataBinder<T, VH> dataBinder, Object... payloads) {
        return dataBinding(dataBinder, IBindPolicy.ofPayloads(payloads));
    }

    default IConfigurationBuilder<T, VH> dataBindingByPayloadsOrNotIncludedPayloads(@NonNull IDataBinder<T, VH> dataBinder, Object... payloads) {
        return dataBinding(dataBinder, IBindPolicy.ofPayloads(payloads).or(IBindPolicy.BIND_POLICY_NOT_INCLUDED_PAYLOADS));
    }

    @NonNull
    @Override
    IConfigurationBuilder<T, VH> addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    @Override
    IConfigurationBuilder<T, VH> addOnClickItemViewListener(@NonNull OnClickItemViewListener<T, VH> clickItemViewListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    @Override
    IConfigurationBuilder<T, VH> addOnLongClickItemViewListener(@NonNull OnLongClickItemViewListener<T, VH> longClickItemViewListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    IConfigurationBuilder<T, VH> plugin(@NonNull IPlugin<T, VH> plugin);

    @NonNull
    IConfiguration<T, VH> build(@NonNull LayoutInflater layoutInflater, @NonNull Consumer<IConfiguration<T, VH>> consumer);

    @NonNull
    default IConfiguration<T, VH> build(@NonNull RecyclerView recyclerView) {
        return build(LayoutInflater.from(recyclerView.getContext()), (configuration) -> {
            recyclerView.setAdapter(configuration.getAdapter());
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        });
    }
}
