package org.jzl.android.recyclerview.v3.core.module;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.IBindPolicy;
import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IContext;
import org.jzl.android.recyclerview.v3.core.IDataBinder;
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IOptionsBuilder;
import org.jzl.android.recyclerview.v3.core.IViewFactoryOwner;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderFactory;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.ListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.OnAttachedToRecyclerViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnDetachedFromRecyclerViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewAttachedToWindowListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewDetachedFromWindowListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewRecycledListener;
import org.jzl.lang.fun.Function;

import java.util.ArrayList;
import java.util.List;

public class MultipleModule<T, VH extends IViewHolder> implements IMultipleModule<T, VH> {

    private final IViewHolderFactory<VH> viewHolderFactory;
    private final List<IRegistrar<T, VH>> registrars = new ArrayList<>();
    private final IListenerManager<T, VH> listenerManager;

    public MultipleModule(@NonNull IViewHolderFactory<VH> viewHolderFactory) {
        this(viewHolderFactory, new ListenerManager<>());
    }

    MultipleModule(@NonNull IViewHolderFactory<VH> viewHolderFactory, @NonNull IListenerManager<T, VH> listenerManager) {
        this.viewHolderFactory = viewHolderFactory;
        this.listenerManager = listenerManager;
    }

    @NonNull
    @Override
    public IOptions<T, VH> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<T> dataGetter) {
        IOptionsBuilder<T, VH> optionsBuilder = configuration.options(this, viewHolderFactory, dataGetter, listenerManager);
        for (int i = 0; i < registrars.size(); i++) {
            IRegistrar<T, VH> registrar = registrars.get(i);
            registrar.registered(configuration, optionsBuilder, dataGetter);
        }
        return optionsBuilder.build();
    }

    @Override
    public <T1, VH1 extends VH> void registered(@NonNull IModule<T1, VH1> module, @NonNull Function<T, T1> mapper) {
        registered(new ModuleRegistrar<>(module, mapper));
    }

    @Override
    public void registered(@NonNull IRegistrar<T, VH> registrar) {
        if (!registrars.contains(registrar)) {
            registrars.add(registrar);
        }
    }

    @SuppressWarnings("unchecked")
    private static class ModuleRegistrar<T, VH extends IViewHolder, T1, VH1 extends VH> implements IRegistrar<T, VH>,
            IDataGetter<T1>, IDataBinder<T, VH>, IBindPolicy, OnViewDetachedFromWindowListener<T, VH>, OnViewAttachedToWindowListener<T, VH>,
            OnDetachedFromRecyclerViewListener<T, VH>, OnAttachedToRecyclerViewListener<T, VH>, OnViewRecycledListener<T, VH> {

        private final IModule<T1, VH1> module;
        private final Function<T, T1> mapper;

        private IOptions<T1, VH1> options;
        private IDataGetter<T> dataGetter;

        private ModuleRegistrar(@NonNull IModule<T1, VH1> module, @NonNull Function<T, T1> mapper) {
            this.module = module;
            this.mapper = mapper;
        }

        @Override
        public void registered(@NonNull IConfiguration<?, ?> configuration, @NonNull IOptionsBuilder<T, VH> optionsBuilder, @NonNull IDataGetter<T> dataGetter) {
            this.dataGetter = dataGetter;
            this.options = module.setup(configuration, this);
            optionsBuilder
                    .createItemView((IViewFactoryOwner<VH>) options)
                    .dataBinding(this, this)
                    .addDetachedFromRecyclerViewListener(this)
                    .addAttachedToRecyclerViewListener(this)
                    .addViewRecycledListener(this, this)
                    .addViewDetachedFromWindowListener(this, this)
                    .addViewAttachedToWindowListener(this, this);

        }

        @Override
        public T1 getData(int position) {
            return mapper.apply(dataGetter.getData(position));
        }

        @Override
        public void binding(IContext context, VH viewHolder, T data) {
            options.getDataBinder().binding(context, (VH1) viewHolder, mapper.apply(data));
        }

        @Override
        public boolean match(@NonNull IContext context) {
            return context.getOptions() == options;
        }

        @Override
        public void onViewAttachedToWindow(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> owner) {
            this.options.notifyViewAttachedToWindow((IViewHolderOwner<VH1>) owner);
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> owner) {
            this.options.notifyViewDetachedFromWindow((IViewHolderOwner<VH1>) owner);
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView, @NonNull IOptions<T, VH> options) {
            this.options.notifyAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView, @NonNull IOptions<T, VH> options) {
            this.options.notifyDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewRecycled(@NonNull IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner) {
            this.options.notifyViewRecycled((IViewHolderOwner<VH1>) viewHolderOwner);
        }
    }

}
