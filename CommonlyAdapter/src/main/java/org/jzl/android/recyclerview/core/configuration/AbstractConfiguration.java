package org.jzl.android.recyclerview.core.configuration;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.LayoutManagerHelper;
import org.jzl.android.recyclerview.core.data.DataClassifier;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.core.diff.IDiffDispatcher;
import org.jzl.android.recyclerview.core.factory.AdapterDataObserverFactory;
import org.jzl.android.recyclerview.core.factory.AdapterFactory;
import org.jzl.android.recyclerview.core.factory.ItemDataHolderFactory;
import org.jzl.android.recyclerview.core.factory.ItemViewFactory;
import org.jzl.android.recyclerview.core.factory.LayoutManagerFactory;
import org.jzl.android.recyclerview.core.item.ItemBindingMatchPolicy;
import org.jzl.android.recyclerview.core.item.ItemDataUpdate;
import org.jzl.android.recyclerview.core.item.ItemDataUpdateHelper;
import org.jzl.android.recyclerview.core.observer.AdapterDataObservable;
import org.jzl.android.recyclerview.core.vh.DataBinder;
import org.jzl.android.recyclerview.core.vh.DataBindingMatchPolicy;
import org.jzl.android.recyclerview.core.vh.ViewBindHelper;
import org.jzl.android.recyclerview.core.vh.ViewHolderFactory;
import org.jzl.android.recyclerview.manager.group.GroupManager;
import org.jzl.android.recyclerview.manager.layout.EmptyLayoutManager;
import org.jzl.android.recyclerview.manager.listener.ListenerManager;
import org.jzl.android.recyclerview.manager.listener.OnAttachedToRecyclerViewListener;
import org.jzl.android.recyclerview.manager.listener.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.manager.listener.OnDetachedFromRecyclerViewListener;
import org.jzl.android.recyclerview.manager.listener.OnItemClickListener;
import org.jzl.android.recyclerview.manager.listener.OnItemLongClickListener;
import org.jzl.android.recyclerview.manager.listener.OnViewAttachedToWindowListener;
import org.jzl.android.recyclerview.manager.listener.OnViewDetachedFromWindowListener;
import org.jzl.android.recyclerview.manager.listener.OnViewRecycledListener;
import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;
import org.jzl.lang.util.holder.BinaryHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract class AbstractConfiguration<V extends View, T, VH extends RecyclerView.ViewHolder> implements Configuration<T, VH> {

    protected final Handler mainHandler;
    protected final ExecutorService executorService;
    protected final Activity currentActivity;

    protected final ItemDataHolderFactory<T, VH> itemDataHolderFactory;
    protected final ViewHolderFactory<VH> viewHolderFactory;
    protected final ListenerManager<T, VH> listenerManager;
    protected final LayoutManagerFactory layoutManagerFactory;
    protected final AdapterFactory<T, VH> adapterFactory;
    protected final ViewBindHelper<T, VH> viewBindHelper;
    protected final DataProvider<T, VH> dataProvider;
    protected final IDiffDispatcher<T, VH> diffDispatcher;
    protected final ItemDataUpdateHelper<T, VH> itemDataUpdateHelper;
    protected final DataClassifier<T, VH> dataClassifier;
    protected final LayoutManagerHelper<T, VH> layoutManagerHelper;
    protected final EmptyLayoutManager<T, VH> emptyLayoutManager;
    protected final GroupManager<T, VH> groupManager;

    protected final AdapterDataObserverFactory<T, VH> adapterDataObserverFactory;

    protected final List<BinaryHolder<ItemBindingMatchPolicy, ItemViewFactory>> itemViewFactories;
    protected final List<BinaryHolder<DataBindingMatchPolicy, DataBinder<T, VH>>> dataBinders;
    protected final List<ItemDataUpdate<T, VH>> itemDataUpdates;

    protected final List<OnAttachedToRecyclerViewListener<T, VH>> attachedToRecyclerViewListeners;
    protected final List<OnCreatedViewHolderListener<T, VH>> createdViewHolderListeners;
    protected final List<OnDetachedFromRecyclerViewListener<T, VH>> detachedFromRecyclerViewListeners;
    protected final List<OnViewAttachedToWindowListener<T, VH>> viewAttachedToWindowListeners;
    protected final List<OnViewDetachedFromWindowListener<T, VH>> viewDetachedFromWindowListeners;
    protected final List<OnViewRecycledListener<T, VH>> viewRecycledListeners;
    protected final List<BinaryHolder<DataBindingMatchPolicy, OnItemClickListener<T, VH>>> itemClickListeners;
    protected final List<BinaryHolder<DataBindingMatchPolicy, OnItemLongClickListener<T, VH>>> itemLongClickListeners;

    protected final List<Component<T, VH>> components;

    protected LayoutInflater layoutInflater;
    protected AdapterDataObservable adapterDataObservable;
    protected V targetView;
    protected RecyclerView.Adapter<VH> adapter;

    public AbstractConfiguration(V targetView, ConfigurationBuilderImpl<T, VH> configurationBuilder) {
        this.targetView = targetView;
        this.itemDataHolderFactory = configurationBuilder.itemDataHolderFactory;
        this.viewHolderFactory = configurationBuilder.viewHolderFactory;
        this.listenerManager = configurationBuilder.listenerManager;
        this.layoutManagerFactory = configurationBuilder.layoutManagerFactory;
        this.adapterFactory = configurationBuilder.adapterFactory;
        this.adapterDataObserverFactory = configurationBuilder.adapterDataObserverFactory;
        this.itemDataUpdateHelper = configurationBuilder.itemDataUpdateHelper;
        this.dataClassifier = configurationBuilder.dataClassifier;

        this.mainHandler = ObjectUtils.get(configurationBuilder.mainHandler, () -> new Handler(Looper.getMainLooper()));
        this.executorService = ObjectUtils.get(configurationBuilder.executorService, Executors::newSingleThreadScheduledExecutor);
        this.currentActivity = configurationBuilder.currentActivity;

        this.dataProvider = configurationBuilder.dataProvider;
        this.layoutManagerHelper = configurationBuilder.layoutManagerHelper;
        this.diffDispatcher = configurationBuilder.diffDispatcher;

        this.viewBindHelper = configurationBuilder.viewBindHelper;
        this.itemViewFactories = new ArrayList<>(configurationBuilder.itemViewFactories);
        this.dataBinders = new ArrayList<>(configurationBuilder.dataBinders);
        this.itemDataUpdates = new ArrayList<>(configurationBuilder.itemDataUpdates);
        this.components = new ArrayList<>(configurationBuilder.components);
        this.emptyLayoutManager = configurationBuilder.emptyLayoutManager;
        this.groupManager = configurationBuilder.groupManager;

        this.attachedToRecyclerViewListeners = new CopyOnWriteArrayList<>(configurationBuilder.attachedToRecyclerViewListeners);
        this.createdViewHolderListeners = new CopyOnWriteArrayList<>(configurationBuilder.createdViewHolderListeners);
        this.detachedFromRecyclerViewListeners = new CopyOnWriteArrayList<>(configurationBuilder.detachedFromRecyclerViewListeners);
        this.viewAttachedToWindowListeners = new CopyOnWriteArrayList<>(configurationBuilder.viewAttachedToWindowListeners);
        this.viewDetachedFromWindowListeners = new CopyOnWriteArrayList<>(configurationBuilder.viewDetachedFromWindowListeners);
        this.viewRecycledListeners = new CopyOnWriteArrayList<>(configurationBuilder.viewRecycledListeners);
        this.itemClickListeners = new CopyOnWriteArrayList<>(configurationBuilder.itemClickListeners);
        this.itemLongClickListeners = new CopyOnWriteArrayList<>(configurationBuilder.itemLongClickListeners);
        initialise(targetView);
    }

    private void initialise(V targetView) {
        this.layoutInflater = LayoutInflater.from(targetView.getContext());
        adapter = adapterFactory.createAdapter(this);
        adapterDataObservable = adapterDataObserverFactory.createAdapterDataObserver(this, adapter);
        initialise(targetView, adapter);
    }

    protected abstract void initialise(V targetView, RecyclerView.Adapter<VH> adapter);

    @Override
    public Activity getCurrentActivity() {
        if (ObjectUtils.nonNull(currentActivity)) {
            return currentActivity;
        }
        Context context = targetView.getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    @Override
    public Handler getMainHandler() {
        return mainHandler;
    }

    @Override
    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public EmptyLayoutManager<T, VH> getEmptyLayoutManager() {
        return emptyLayoutManager;
    }

    @Override
    public GroupManager<T, VH> getGroupManager() {
        return groupManager;
    }

    @Override
    public IDiffDispatcher<T, VH> getDiffDispatcher() {
        return diffDispatcher;
    }

    public RecyclerView.Adapter<VH> getAdapter() {
        return adapter;
    }

    public LayoutManagerFactory getLayoutManagerFactory() {
        return layoutManagerFactory;
    }

    @Override
    public ItemDataHolderFactory<T, VH> getItemDataHolderFactory() {
        return itemDataHolderFactory;
    }

    @Override
    public DataProvider<T, VH> getDataProvider() {
        return dataProvider;
    }

    @Override
    public DataClassifier<T, VH> getDataClassifier() {
        return dataClassifier;
    }

    @Override
    public ViewHolderFactory<VH> getViewHolderFactory() {
        return viewHolderFactory;
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    @Override
    public ItemViewFactory matchItemViewFactory(int viewType) {
        BinaryHolder<ItemBindingMatchPolicy, ItemViewFactory> holder = ForeachUtils.findByOne(this.itemViewFactories, target -> target.one.match(viewType));
        if (ObjectUtils.nonNull(holder)) {
            return holder.two;
        }
        return null;
    }

    @Override
    public ViewBindHelper<T, VH> getViewBindHelper() {
        return viewBindHelper;
    }

    @Override
    public ItemDataUpdateHelper<T, VH> getItemDataUpdateHelper() {
        return itemDataUpdateHelper;
    }

    @Override
    public ListenerManager<T, VH> getListenerManager() {
        return listenerManager;
    }

    @Override
    public AdapterDataObservable getAdapterDataObservable() {
        return adapterDataObservable;
    }

    @Override
    public Iterable<ItemDataUpdate<T, VH>> getItemDataUpdates() {
        return itemDataUpdates;
    }

    @Override
    public Iterable<Component<T, VH>> getComponents() {
        return components;
    }

    @Override
    public Iterable<BinaryHolder<DataBindingMatchPolicy, DataBinder<T, VH>>> getDataBinders() {
        return dataBinders;
    }

    @Override
    public Iterable<OnAttachedToRecyclerViewListener<T, VH>> getOnAttachedToRecyclerViewListeners() {
        return this.attachedToRecyclerViewListeners;
    }

    @Override
    public Iterable<OnCreatedViewHolderListener<T, VH>> getOnCreatedViewHolderListeners() {
        return this.createdViewHolderListeners;
    }

    @Override
    public Iterable<OnDetachedFromRecyclerViewListener<T, VH>> getOnDetachedFromRecyclerViewListeners() {
        return this.detachedFromRecyclerViewListeners;
    }

    @Override
    public Iterable<OnViewAttachedToWindowListener<T, VH>> getOnViewAttachedToWindowListeners() {
        return this.viewAttachedToWindowListeners;
    }

    @Override
    public Iterable<OnViewDetachedFromWindowListener<T, VH>> getOnViewDetachedFromWindowListeners() {
        return this.viewDetachedFromWindowListeners;
    }

    @Override
    public Iterable<OnViewRecycledListener<T, VH>> getOnViewRecycledListeners() {
        return this.viewRecycledListeners;
    }

    @Override
    public Iterable<BinaryHolder<DataBindingMatchPolicy, OnItemClickListener<T, VH>>> getOnItemClickListeners() {
        return itemClickListeners;
    }

    @Override
    public Iterable<BinaryHolder<DataBindingMatchPolicy, OnItemLongClickListener<T, VH>>> getOnItemLongClickListeners() {
        return itemLongClickListeners;
    }

    @Override
    @SuppressWarnings("all")
    public <C extends Component<T, VH>> C getComponent(Class<C> type) {
        for (Component<T, VH> component : components) {
            if (type.isInstance(component)) {
                return (C) component;
            }
        }
        return null;
    }

    @Override
    public Configuration<T, VH> addOnAttachedToRecyclerViewListener(OnAttachedToRecyclerViewListener<T, VH> attachedToRecyclerViewListener) {
        if (ObjectUtils.nonNull(attachedToRecyclerViewListener)) {
            this.attachedToRecyclerViewListeners.add(attachedToRecyclerViewListener);
        }
        return this;
    }

    @Override
    public Configuration<T, VH> addOnCreatedViewHolderListener(OnCreatedViewHolderListener<T, VH> createdViewHolderListener) {
        if (ObjectUtils.nonNull(createdViewHolderListener)) {
            this.createdViewHolderListeners.add(createdViewHolderListener);
        }
        return this;
    }

    @Override
    public Configuration<T, VH> addOnDetachedFromRecyclerViewListener(OnDetachedFromRecyclerViewListener<T, VH> detachedFromRecyclerViewListener) {
        if (ObjectUtils.nonNull(detachedFromRecyclerViewListener)) {
            this.detachedFromRecyclerViewListeners.add(detachedFromRecyclerViewListener);
        }
        return this;
    }

    @Override
    public Configuration<T, VH> addOnViewAttachedToWindowListener(OnViewAttachedToWindowListener<T, VH> viewAttachedToWindowListener) {
        if (ObjectUtils.nonNull(viewAttachedToWindowListener)) {
            this.viewAttachedToWindowListeners.add(viewAttachedToWindowListener);
        }
        return this;
    }

    @Override
    public Configuration<T, VH> addOnViewDetachedFromWindowListener(OnViewDetachedFromWindowListener<T, VH> viewDetachedFromWindowListener) {
        if (ObjectUtils.nonNull(viewDetachedFromWindowListener)) {
            this.viewDetachedFromWindowListeners.add(viewDetachedFromWindowListener);
        }
        return this;
    }

    @Override
    public Configuration<T, VH> addOnViewRecycledListener(OnViewRecycledListener<T, VH> viewRecycledListener) {
        if (ObjectUtils.nonNull(viewRecycledListener)) {
            this.viewRecycledListeners.add(viewRecycledListener);
        }
        return this;
    }

    @Override
    public Configuration<T, VH> addOnItemClickListener(OnItemClickListener<T, VH> itemClickListener, DataBindingMatchPolicy matchPolicy) {
        if (ObjectUtils.nonNull(itemClickListener) && ObjectUtils.nonNull(matchPolicy)) {
            this.itemClickListeners.add(BinaryHolder.of(matchPolicy, itemClickListener));
        }
        return this;
    }

    @Override
    public Configuration<T, VH> addOnItemLongClickListener(OnItemLongClickListener<T, VH> itemLongClickListener, DataBindingMatchPolicy matchPolicy) {
        if (ObjectUtils.nonNull(itemLongClickListener) && ObjectUtils.nonNull(matchPolicy)) {
            this.itemLongClickListeners.add(BinaryHolder.of(matchPolicy, itemLongClickListener));
        }
        return this;
    }

    @Override
    public LayoutManagerHelper<T, VH> getLayoutManagerHelper() {
        return layoutManagerHelper;
    }

}
