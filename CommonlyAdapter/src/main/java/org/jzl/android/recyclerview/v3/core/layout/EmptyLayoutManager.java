package org.jzl.android.recyclerview.v3.core.layout;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IAdapterObservable;
import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IConfigurationBuilder;
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.IMatchPolicy;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IPlugin;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.components.IComponent;
import org.jzl.android.recyclerview.v3.core.module.IAdapterModule;
import org.jzl.android.recyclerview.v3.core.module.IAdapterModuleProxy;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.lang.fun.Function;

import java.util.concurrent.atomic.AtomicReference;

class EmptyLayoutManager<T, VH extends IViewHolder, T2, VH2 extends VH> implements IPlugin<T, VH>,
        IEmptyLayoutManager<T, VH>,
        IAdapterModuleProxy.IProxy<T, VH>, IAdapterObservable.IDataChangedObserver<T, VH>, IComponent<T, VH> {

    private final int emptyLayoutItemViewType;
    private T emptyLayoutData;
    private final IModule<T2, VH2> module;
    private final Function<T, T2> mapper;
    private final AtomicReference<Status> status = new AtomicReference<>(Status.EMPTY);
    private IAdapterObservable<T, VH> adapterObservable;

    EmptyLayoutManager(int emptyLayoutItemViewType, T emptyLayoutData, IModule<T2, VH2> module, Function<T, T2> mapper) {
        this.emptyLayoutItemViewType = emptyLayoutItemViewType;
        this.emptyLayoutData = emptyLayoutData;
        this.module = module;
        this.mapper = mapper;
    }

    @Override
    public void setup(@NonNull IConfigurationBuilder<T, VH> builder) {
        builder.proxy(this).registered(module, mapper).addComponent(this);
    }

    @Override
    public void initialize(IConfiguration<T, VH> configuration) {
        status.set(isEmpty(configuration) ? Status.EMPTY : Status.NO_EMPTY);
        adapterObservable = configuration.getAdapterObservable();
        adapterObservable.observe(this);
        configuration.getRecyclerViewLayoutManager().spanSize(SpanSize.ALL, IMatchPolicy.ofItemTypes(emptyLayoutItemViewType), Integer.MAX_VALUE);
    }

    @NonNull
    @Override
    public IAdapterModule<T, VH> proxy(@NonNull IConfiguration<T, VH> configuration, @NonNull IAdapterModule<T, VH> target) {
        return new EmptyLayoutAdapterModuleProxy(target);
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void updateEmptyLayoutData(T emptyLayoutData) {
        this.emptyLayoutData = emptyLayoutData;
        if (isEmptyLayout()) {
            adapterObservable.notifyItemRangeChanged(0, 1);
        }
    }

    @Override
    public boolean isEmptyLayout() {
        return status.get() == Status.EMPTY;
    }

    public Status getStatus() {
        return status.get();
    }

    private boolean isEmpty(IConfiguration<T, VH> configuration) {
        return configuration.isDataEmpty();
    }

    private int getEmptyLayoutItemCount(IConfiguration<T, VH> configuration) {
        return 1;
    }

    private int getEmptyLayoutItemViewType(IConfiguration<T, VH> configuration, int position) {
        return emptyLayoutItemViewType;
    }

    private T getEmptyLayoutData(IConfiguration<T, VH> configuration, int position) {
        return emptyLayoutData;
    }

    @Override
    public void onBeforeDataChanged(@NonNull IConfiguration<T, VH> configuration, @NonNull IAdapterObservable<T, VH> adapterObservable, @NonNull IAdapterObservable.Event event) {
        if (!isEmpty(configuration) && status.compareAndSet(Status.EMPTY, Status.NO_EMPTY)) {
            adapterObservable.notifyItemRangeRemoved(0, 1);
        }
    }

    @Override
    public void onAfterDataChanged(@NonNull IConfiguration<T, VH> configuration, @NonNull IAdapterObservable<T, VH> adapterObservable, @NonNull IAdapterObservable.Event event) {
        if (isEmpty(configuration) && status.compareAndSet(Status.NO_EMPTY, Status.EMPTY)) {
            adapterObservable.notifyItemRangeInserted(0, 1);
        }
    }

    class EmptyLayoutAdapterModuleProxy implements IAdapterModule<T, VH> {

        private final IAdapterModule<T, VH> adapterModule;

        public EmptyLayoutAdapterModuleProxy(IAdapterModule<T, VH> adapterModule) {
            this.adapterModule = adapterModule;
        }

        @Override
        public <T1, VH1 extends VH> void registered(@NonNull IModule<T1, VH1> module, @NonNull Function<T, T1> mapper) {
            adapterModule.registered(module, mapper);
        }

        @Override
        public void registered(@NonNull IRegistrar<T, VH> registrar) {
            adapterModule.registered(registrar);
        }

        @NonNull
        @Override
        public IOptions<T, VH> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<T> dataGetter) {
            return adapterModule.setup(configuration, dataGetter);
        }

        @Override
        public int getItemCount(IConfiguration<T, VH> configuration) {
            if (isEmpty(configuration)) {
                return getEmptyLayoutItemCount(configuration);
            }
            return adapterModule.getItemCount(configuration);
        }

        @Override
        public int getItemViewType(IConfiguration<T, VH> configuration, int position) {
            if (isEmpty(configuration)) {
                return getEmptyLayoutItemViewType(configuration, position);
            } else {
                return adapterModule.getItemViewType(configuration, position);
            }
        }

        @Override
        public long getItemId(IConfiguration<T, VH> configuration, int position) {
            return adapterModule.getItemId(configuration, position);
        }

        @Override
        public T getItemData(@NonNull IConfiguration<T, VH> configuration, int position) {
            if (isEmpty(configuration)) {
                return getEmptyLayoutData(configuration, position);
            } else {
                return adapterModule.getItemData(configuration, position);
            }
        }

        @Override
        public <VH1 extends VH> void registered(@NonNull IModule<T, VH1> module) {
            adapterModule.registered(module);
        }
    }

    public enum Status {
        EMPTY, NO_EMPTY
    }
}
