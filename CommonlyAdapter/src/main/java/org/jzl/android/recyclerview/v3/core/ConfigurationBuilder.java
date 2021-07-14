package org.jzl.android.recyclerview.v3.core;

import android.os.Handler;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.components.ComponentManager;
import org.jzl.android.recyclerview.v3.core.components.IComponent;
import org.jzl.android.recyclerview.v3.core.components.IComponentManager;
import org.jzl.android.recyclerview.v3.core.layout.ILayoutManagerFactory;
import org.jzl.android.recyclerview.v3.core.layout.ISpanSizeLookup;
import org.jzl.android.recyclerview.v3.core.layout.RecyclerViewLayoutManager;
import org.jzl.android.recyclerview.v3.core.listeners.IListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.ListenerManager;
import org.jzl.android.recyclerview.v3.core.listeners.OnAttachedToRecyclerViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnDetachedFromRecyclerViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnLongClickItemViewListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewAttachedToWindowListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewDetachedFromWindowListener;
import org.jzl.android.recyclerview.v3.core.listeners.OnViewRecycledListener;
import org.jzl.android.recyclerview.v3.core.module.AdapterModuleProxy;
import org.jzl.android.recyclerview.v3.core.module.IAdapterModule;
import org.jzl.android.recyclerview.v3.core.module.IAdapterModuleProxy;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.android.recyclerview.v3.model.IClassifiable;
import org.jzl.android.recyclerview.v3.model.Identifiable;
import org.jzl.lang.fun.Consumer;
import org.jzl.lang.fun.Function;
import org.jzl.lang.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

class ConfigurationBuilder<T, VH extends IViewHolder> implements IConfigurationBuilder<T, VH> {

    final IAdapterModule<T, VH> adapterModule;
    List<T> dataProvider;
    IDataClassifier<T, VH> dataClassifier = (configuration, data, position) -> {
        if (data instanceof IClassifiable) {
            return ((IClassifiable) data).getItemType();
        }
        return 1;
    };
    IIdentityProvider<T, VH> identityProvider = (configuration, data, position) -> {
        if (data instanceof Identifiable) {
            return ((Identifiable) data).getId();
        }
        return RecyclerView.NO_ID;
    };

    final IListenerManager<T, VH> listenerManager;
    private final List<IPlugin<T, VH>> plugins = new ArrayList<>();
    final IComponentManager<T, VH> componentManager = new ComponentManager<>();
    final RecyclerViewLayoutManager<T, VH> recyclerViewLayoutManager = new RecyclerViewLayoutManager<>();

    final IAdapterModuleProxy<T, VH> adapterModuleProxy = new AdapterModuleProxy<>();
    Handler mainHandler;
    ExecutorService executorService;

    public ConfigurationBuilder(@NonNull IViewHolderFactory<VH> viewHolderFactory) {
        listenerManager = new ListenerManager<>();
        this.adapterModule = IAdapterModule.of(viewHolderFactory, listenerManager);
        initialize();
    }

    private void initialize() {
        addComponent(recyclerViewLayoutManager);
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> setMainHandler(@NonNull Handler mainHandler) {
        this.mainHandler = mainHandler;
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> setExecutorService(@NonNull ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> setDataProvider(List<T> dataProvider) {
        this.dataProvider = dataProvider;
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> setDataClassifier(IDataClassifier<T, VH> dataClassifier) {
        this.dataClassifier = ObjectUtils.get(dataClassifier, this.dataClassifier);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> setIdentityProvider(IIdentityProvider<T, VH> identityProvider) {
        this.identityProvider = ObjectUtils.get(identityProvider, this.identityProvider);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> layoutManager(@NonNull ILayoutManagerFactory<T, VH> layoutManager) {
        recyclerViewLayoutManager.layoutManager(layoutManager);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> setSpanSizeLookup(@NonNull ISpanSizeLookup<T, VH> spanSizeLookup) {
        recyclerViewLayoutManager.setSpanSizeLookup(spanSizeLookup);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> proxy(@NonNull IAdapterModuleProxy.IProxy<T, VH> proxy) {
        adapterModuleProxy.addProxy(proxy);
        return this;
    }

    @NonNull
    public <T1, VH1 extends VH> IConfigurationBuilder<T, VH> registered(@NonNull IModule<T1, VH1> module, @NonNull Function<T, T1> mapper) {
        adapterModule.registered(module, mapper);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> createItemView(@NonNull Function<IOptions<T, VH>, IViewFactoryOwner<VH>> consumer) {
        adapterModule.registered((configuration, optionsBuilder, dataGetter) -> optionsBuilder.createItemView(consumer));
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> dataBinding(@NonNull IDataBinder<T, VH> dataBinder, @NonNull IBindPolicy bindPolicy, int priority) {
        adapterModule.registered((configuration, optionsBuilder, dataGetter) -> optionsBuilder.dataBinding(dataBinder, bindPolicy, priority));
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, @NonNull IMatchPolicy matchPolicy) {
        listenerManager.addOnCreatedViewHolderListener(createdViewHolderListener, matchPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> addOnClickItemViewListener(@NonNull OnClickItemViewListener<T, VH> clickItemViewListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addOnClickItemViewListener(clickItemViewListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> addOnLongClickItemViewListener(@NonNull OnLongClickItemViewListener<T, VH> longClickItemViewListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addOnLongClickItemViewListener(longClickItemViewListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> addViewAttachedToWindowListener(@NonNull OnViewAttachedToWindowListener<T, VH> viewAttachedToWindowListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewAttachedToWindowListener(viewAttachedToWindowListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> addViewDetachedFromWindowListener(@NonNull OnViewDetachedFromWindowListener<T, VH> viewDetachedFromWindowListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewDetachedFromWindowListener(viewDetachedFromWindowListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> addAttachedToRecyclerViewListener(@NonNull OnAttachedToRecyclerViewListener<T, VH> attachedToRecyclerViewListener) {
        listenerManager.addAttachedToRecyclerViewListener(attachedToRecyclerViewListener);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> addDetachedFromRecyclerViewListener(@NonNull OnDetachedFromRecyclerViewListener<T, VH> detachedFromRecyclerViewListener) {
        listenerManager.addDetachedFromRecyclerViewListener(detachedFromRecyclerViewListener);
        return this;
    }

    @Override
    public IConfigurationBuilder<T, VH> addViewRecycledListener(@NonNull OnViewRecycledListener<T, VH> viewRecycledListener, @NonNull IBindPolicy bindPolicy) {
        listenerManager.addViewRecycledListener(viewRecycledListener, bindPolicy);
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> plugin(@NonNull IPlugin<T, VH> plugin) {
        if (!plugins.contains(plugin)) {
            plugins.add(plugin);
        }
        return this;
    }

    @NonNull
    @Override
    public IConfigurationBuilder<T, VH> addComponent(@NonNull IComponent<T, VH> component) {
        componentManager.addComponent(component);
        return this;
    }

    @NonNull
    @Override
    public IConfiguration<T, VH> build(@NonNull LayoutInflater layoutInflater, @NonNull Consumer<IConfiguration<T, VH>> consumer) {
        applyPlugins();
        Configuration<T, VH> configuration = new Configuration<>(this, layoutInflater);
        componentManager.initialize(configuration);
        consumer.accept(configuration);
        return configuration;
    }

    private void applyPlugins() {
        for (int i = 0; i < plugins.size(); i++) {
            plugins.get(i).setup(this);
        }
    }
}
