package org.jzl.android.recyclerview.core.configuration;

import android.app.Activity;
import android.os.Handler;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.jzl.android.recyclerview.core.CommonlyAdapter;
import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.LayoutManagerHelper;
import org.jzl.android.recyclerview.core.Plugin;
import org.jzl.android.recyclerview.core.SpanSizeLookup;
import org.jzl.android.recyclerview.core.data.Classifiable;
import org.jzl.android.recyclerview.core.data.DataClassifier;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.core.diff.AsyncDiffDispatcher;
import org.jzl.android.recyclerview.core.diff.IDiffDispatcher;
import org.jzl.android.recyclerview.core.factory.AdapterDataObserverFactory;
import org.jzl.android.recyclerview.core.factory.AdapterFactory;
import org.jzl.android.recyclerview.core.factory.ItemDataHolderFactory;
import org.jzl.android.recyclerview.core.factory.ItemViewFactory;
import org.jzl.android.recyclerview.core.factory.LayoutManagerFactory;
import org.jzl.android.recyclerview.core.item.DefaultItemDataUpdateHelper;
import org.jzl.android.recyclerview.core.item.ItemBindingMatchPolicy;
import org.jzl.android.recyclerview.core.item.ItemDataHolderImpl;
import org.jzl.android.recyclerview.core.item.ItemDataUpdate;
import org.jzl.android.recyclerview.core.item.ItemDataUpdateHelper;
import org.jzl.android.recyclerview.core.observer.DefaultAdapterDataObservable;
import org.jzl.android.recyclerview.core.vh.DataBinder;
import org.jzl.android.recyclerview.core.vh.DataBindingMatchPolicy;
import org.jzl.android.recyclerview.core.vh.DefaultViewBindHelper;
import org.jzl.android.recyclerview.core.vh.ViewBindHelper;
import org.jzl.android.recyclerview.core.vh.ViewHolderFactory;
import org.jzl.android.recyclerview.manager.group.DefaultGroupManager;
import org.jzl.android.recyclerview.manager.group.GroupManager;
import org.jzl.android.recyclerview.manager.layout.DefaultEmptyLayoutManager;
import org.jzl.android.recyclerview.manager.layout.EmptyLayoutManager;
import org.jzl.android.recyclerview.manager.listener.DefaultListenerManager;
import org.jzl.android.recyclerview.manager.listener.ListenerManager;
import org.jzl.android.recyclerview.manager.listener.OnAttachedToRecyclerViewListener;
import org.jzl.android.recyclerview.manager.listener.OnCreatedViewHolderListener;
import org.jzl.android.recyclerview.manager.listener.OnDetachedFromRecyclerViewListener;
import org.jzl.android.recyclerview.manager.listener.OnItemClickListener;
import org.jzl.android.recyclerview.manager.listener.OnItemLongClickListener;
import org.jzl.android.recyclerview.manager.listener.OnViewAttachedToWindowListener;
import org.jzl.android.recyclerview.manager.listener.OnViewDetachedFromWindowListener;
import org.jzl.android.recyclerview.manager.listener.OnViewRecycledListener;
import org.jzl.lang.util.ObjectUtils;
import org.jzl.lang.util.holder.BinaryHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

class ConfigurationBuilderImpl<T, VH extends RecyclerView.ViewHolder> implements ConfigurationBuilder<T, VH> {

    final LayoutManagerHelper<T, VH> layoutManagerHelper = LayoutManagerHelper.of();
    final List<BinaryHolder<ItemBindingMatchPolicy, ItemViewFactory>> itemViewFactories = new ArrayList<>();
    final List<BinaryHolder<DataBindingMatchPolicy, DataBinder<T, VH>>> dataBinders = new ArrayList<>();
    final List<ItemDataUpdate<T, VH>> itemDataUpdates = new ArrayList<>();
    final List<OnAttachedToRecyclerViewListener<T, VH>> attachedToRecyclerViewListeners = new ArrayList<>();
    final List<OnCreatedViewHolderListener<T, VH>> createdViewHolderListeners = new ArrayList<>();
    final List<OnDetachedFromRecyclerViewListener<T, VH>> detachedFromRecyclerViewListeners = new ArrayList<>();
    final List<OnViewAttachedToWindowListener<T, VH>> viewAttachedToWindowListeners = new ArrayList<>();
    final List<OnViewDetachedFromWindowListener<T, VH>> viewDetachedFromWindowListeners = new ArrayList<>();
    final List<OnViewRecycledListener<T, VH>> viewRecycledListeners = new ArrayList<>();
    final List<BinaryHolder<DataBindingMatchPolicy, OnItemClickListener<T, VH>>> itemClickListeners = new ArrayList<>();
    final List<BinaryHolder<DataBindingMatchPolicy, OnItemLongClickListener<T, VH>>> itemLongClickListeners = new ArrayList<>();
    final List<Component<T, VH>> components = new ArrayList<>();
    final ViewHolderFactory<VH> viewHolderFactory;
    private final List<Plugin<T, VH>> plugins = new ArrayList<>();
    AdapterFactory<T, VH> adapterFactory = CommonlyAdapter::new;
    ItemDataHolderFactory<T, VH> itemDataHolderFactory = ItemDataHolderImpl::new;
    AdapterDataObserverFactory<T, VH> adapterDataObserverFactory = (configuration, adapter) -> new DefaultAdapterDataObservable(adapter);
    LayoutManagerFactory layoutManagerFactory = (context, recyclerView1) -> new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    ListenerManager<T, VH> listenerManager = new DefaultListenerManager<>();
    ViewBindHelper<T, VH> viewBindHelper = new DefaultViewBindHelper<>();
    ItemDataUpdateHelper<T, VH> itemDataUpdateHelper = new DefaultItemDataUpdateHelper<>();
    DataProvider<T, VH> dataProvider;
    IDiffDispatcher<T, VH> diffDispatcher = new AsyncDiffDispatcher<>();
    EmptyLayoutManager<T, VH> emptyLayoutManager = new DefaultEmptyLayoutManager<>();
    GroupManager<T, VH> groupManager = new DefaultGroupManager<>();
    DataClassifier<T, VH> dataClassifier = (dataProvider, position) -> {
        T data = dataProvider.get(position);
        if (data instanceof Classifiable) {
            return ((Classifiable) data).getType();
        } else {
            return 0;
        }
    };
    Handler mainHandler;
    ExecutorService executorService;
    Activity currentActivity;

    public ConfigurationBuilderImpl(ViewHolderFactory<VH> viewHolderFactory) {
        this.viewHolderFactory = ObjectUtils.requireNonNull(viewHolderFactory, "viewHolderFactory");
    }

    @Override
    public ConfigurationBuilder<T, VH> setSpanSizeLookup(SpanSizeLookup<T> spanSizeLookup) {
        layoutManagerHelper.setSpanSizeLookup(spanSizeLookup);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setAdapterFactory(AdapterFactory<T, VH> adapterFactory) {
        this.adapterFactory = ObjectUtils.get(adapterFactory, this.adapterFactory);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setLayoutManagerFactory(LayoutManagerFactory layoutManagerFactory) {
        this.layoutManagerFactory = ObjectUtils.get(layoutManagerFactory, this.layoutManagerFactory);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> enableGroupManager() {
        groupManager.enable();
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> disableGroupManager() {
        groupManager.disable();
        return null;
    }

    @Override
    public ConfigurationBuilder<T, VH> setGroupHelper(GroupManager.GroupHelper<T, VH> groupHelper) {
        groupManager.setGroupHelper(groupHelper);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setEmptyLayoutManager(EmptyLayoutManager<T, VH> emptyLayoutManager) {
        this.emptyLayoutManager = ObjectUtils.get(emptyLayoutManager, this.emptyLayoutManager);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setEmptyItemType(int emptyItemType) {
        emptyLayoutManager.setEmptyItemType(emptyItemType);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setEmptyLayoutData(T emptyLayoutData) {
        emptyLayoutManager.setEmptyData(emptyLayoutData);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> enableEmptyLayout() {
        emptyLayoutManager.enable();
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> disableEmptyLayout() {
        emptyLayoutManager.disable();
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> dataBindingByEmptyLayout(DataBinder<T, VH> dataBinder) {
        emptyLayoutManager.dataBinding(dataBinder);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> createEmptyLayoutItemView(ItemViewFactory itemViewFactory) {
        emptyLayoutManager.createItemView(itemViewFactory);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> createEmptyLayoutItemView(int layoutResId) {
        emptyLayoutManager.createItemView(layoutResId);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setItemCallback(DiffUtil.ItemCallback<T> itemCallback) {
        diffDispatcher.setItemCallback(itemCallback);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setDiffDispatcher(IDiffDispatcher<T, VH> diffDispatcher) {
        this.diffDispatcher = ObjectUtils.get(diffDispatcher, this.diffDispatcher);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setViewBindHelper(ViewBindHelper<T, VH> viewBindHelper) {
        this.viewBindHelper = ObjectUtils.get(viewBindHelper, this.viewBindHelper);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setItemDataUpdateHelper(ItemDataUpdateHelper<T, VH> itemDataUpdateHelper) {
        this.itemDataUpdateHelper = ObjectUtils.get(itemDataUpdateHelper, this.itemDataUpdateHelper);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setAdapterDataObserverFactory(AdapterDataObserverFactory<T, VH> adapterDataObserverFactory) {
        this.adapterDataObserverFactory = ObjectUtils.get(adapterDataObserverFactory, this.adapterDataObserverFactory);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setDataProvider(DataProvider<T, VH> dataProvider) {
        this.dataProvider = ObjectUtils.get(dataProvider, this.dataProvider);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setDataClassifier(DataClassifier<T, VH> dataClassifier) {
        this.dataClassifier = ObjectUtils.get(dataClassifier, this.dataClassifier);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setItemDataHolderFactory(ItemDataHolderFactory<T, VH> itemDataHolderFactory) {
        this.itemDataHolderFactory = ObjectUtils.get(itemDataHolderFactory, this.itemDataHolderFactory);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setListenerManager(ListenerManager<T, VH> listenerManager) {
        this.listenerManager = ObjectUtils.get(listenerManager, this.listenerManager);
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> createItemView(int layoutId, int... itemTypes) {
        return createItemView((layoutInflater, parent, viewType) -> layoutInflater.inflate(layoutId, parent, false), itemTypes);
    }

    @Override
    public ConfigurationBuilder<T, VH> createItemView(ItemViewFactory itemViewFactory, int... itemTypes) {
        return createItemView(itemViewFactory, ItemBindingMatchPolicy.of(itemTypes));
    }

    @Override
    public ConfigurationBuilder<T, VH> createItemView(ItemViewFactory itemViewFactory, ItemBindingMatchPolicy matchPolicy) {
        if (ObjectUtils.nonNull(itemViewFactory) && ObjectUtils.nonNull(matchPolicy)) {
            this.itemViewFactories.add(BinaryHolder.of(matchPolicy, itemViewFactory));
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> updateItemData(ItemDataUpdate<T, VH> itemDataUpdate) {
        if (ObjectUtils.nonNull(itemDataUpdate)) {
            this.itemDataUpdates.add(itemDataUpdate);
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> dataBinding(DataBinder<T, VH> dataBinder, DataBindingMatchPolicy matchPolicy) {
        if (ObjectUtils.nonNull(dataBinder) && ObjectUtils.nonNull(matchPolicy)) {
            this.dataBinders.add(BinaryHolder.of(matchPolicy, dataBinder));
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> dataBindingByItemTypes(DataBinder<T, VH> dataBinder, int... itemTypes) {
        return dataBinding(dataBinder, DataBindingMatchPolicy.ofItemTypes(itemTypes));
    }

    @Override
    public ConfigurationBuilder<T, VH> dataBindingByPayloads(DataBinder<T, VH> dataBinder, Object... payloads) {
        return dataBinding(dataBinder, DataBindingMatchPolicy.ofPayloads(payloads));
    }

    @Override
    public ConfigurationBuilder<T, VH> dataBindingByPayloadsOrNotIncludedPayloads(DataBinder<T, VH> dataBinder, Object... payloads) {
        return dataBinding(dataBinder, DataBindingMatchPolicy.MATCH_POLICY_NOT_INCLUDED_PAYLOADS.or(DataBindingMatchPolicy.ofPayloads(payloads)));
    }

    @Override
    public ConfigurationBuilder<T, VH> addOnAttachedToRecyclerViewListener(OnAttachedToRecyclerViewListener<T, VH> attachedToRecyclerViewListener) {
        if (ObjectUtils.nonNull(attachedToRecyclerViewListener)) {
            this.attachedToRecyclerViewListeners.add(attachedToRecyclerViewListener);
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> addOnCreatedViewHolderListener(OnCreatedViewHolderListener<T, VH> createdViewHolderListener) {
        if (ObjectUtils.nonNull(createdViewHolderListener)) {
            this.createdViewHolderListeners.add(createdViewHolderListener);
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> addOnDetachedFromRecyclerViewListener(OnDetachedFromRecyclerViewListener<T, VH> detachedFromRecyclerViewListener) {
        if (ObjectUtils.nonNull(detachedFromRecyclerViewListener)) {
            this.detachedFromRecyclerViewListeners.add(detachedFromRecyclerViewListener);
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> addOnViewAttachedToWindowListener(OnViewAttachedToWindowListener<T, VH> viewAttachedToWindowListener) {
        if (ObjectUtils.nonNull(viewAttachedToWindowListener)) {
            this.viewAttachedToWindowListeners.add(viewAttachedToWindowListener);
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> addOnViewDetachedFromWindowListener(OnViewDetachedFromWindowListener<T, VH> viewDetachedFromWindowListener) {
        if (ObjectUtils.nonNull(viewDetachedFromWindowListener)) {
            this.viewDetachedFromWindowListeners.add(viewDetachedFromWindowListener);
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> addOnViewRecycledListener(OnViewRecycledListener<T, VH> viewRecycledListener) {
        if (ObjectUtils.nonNull(viewRecycledListener)) {
            this.viewRecycledListeners.add(viewRecycledListener);
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> addOnItemClickListener(OnItemClickListener<T, VH> itemClickListener, DataBindingMatchPolicy matchPolicy) {
        if (ObjectUtils.nonNull(itemClickListener) && ObjectUtils.nonNull(matchPolicy)) {
            this.itemClickListeners.add(BinaryHolder.of(matchPolicy, itemClickListener));
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> addOnItemLongClickListener(OnItemLongClickListener<T, VH> itemLongClickListener, DataBindingMatchPolicy matchPolicy) {
        if (ObjectUtils.nonNull(itemLongClickListener) && ObjectUtils.nonNull(matchPolicy)) {
            this.itemLongClickListeners.add(BinaryHolder.of(matchPolicy, itemLongClickListener));
        }
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
        return this;
    }

    @Override
    public ConfigurationBuilder<T, VH> setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
        return this;
    }

    @Override
    public final ConfigurationBuilder<T, VH> component(Component<T, VH> component) {
        if (ObjectUtils.nonNull(component)) {
            this.components.add(component);
        }
        return this;
    }

    @Override
    public final ConfigurationBuilder<T, VH> plugin(Plugin<T, VH> plugin) {
        if (ObjectUtils.nonNull(plugin)) {
            this.plugins.add(plugin);
        }
        return this;
    }

    @Override
    public RecyclerViewConfiguration<T, VH> build(RecyclerView recyclerView) {
        applyComponents(false);
        applyPlugins();
        return new RecyclerViewConfigurationImpl<>(recyclerView, this);
    }

    @Override
    public ViewPagerConfiguration<T, VH> build(ViewPager2 viewPager2) {
        applyComponents(true);
        applyPlugins();
        return new ViewPagerConfigurationImpl<>(viewPager2, this);
    }

    private void applyPlugins() {
        int i = 0;
        while (i < plugins.size()) {
            plugins.get(i).setup(this);
            i++;
        }
        plugins.clear();
    }

    private void applyComponents(boolean isViewPager) {
        component(listenerManager)
                .plugin(emptyLayoutManager)
                .plugin(groupManager)
                .component(itemDataUpdateHelper)
                .component(dataProvider)
                .component(diffDispatcher)
                .component(viewBindHelper);
        if (!isViewPager) {
            plugin(layoutManagerHelper);
        }
    }
}
