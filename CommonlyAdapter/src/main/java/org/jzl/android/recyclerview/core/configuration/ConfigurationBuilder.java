package org.jzl.android.recyclerview.core.configuration;

import android.app.Activity;
import android.os.Handler;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.Plugin;
import org.jzl.android.recyclerview.core.SpanSizeLookup;
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
import org.jzl.android.recyclerview.core.vh.DataBinder;
import org.jzl.android.recyclerview.core.vh.DataBindingMatchPolicy;
import org.jzl.android.recyclerview.core.vh.ViewBindHelper;
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

import java.util.concurrent.ExecutorService;

public interface ConfigurationBuilder<T, VH extends RecyclerView.ViewHolder> {

    ConfigurationBuilder<T, VH> enableGroupManager();

    ConfigurationBuilder<T, VH> disableGroupManager();

    ConfigurationBuilder<T, VH> setGroupHelper(GroupManager.GroupHelper<T, VH> groupHelper);


    ConfigurationBuilder<T, VH> setEmptyLayoutManager(EmptyLayoutManager<T, VH> emptyLayoutManager);

    ConfigurationBuilder<T, VH> setEmptyItemType(int emptyItemType);

    ConfigurationBuilder<T, VH> setEmptyLayoutData(T emptyLayoutData);

    ConfigurationBuilder<T, VH> enableEmptyLayout();

    ConfigurationBuilder<T, VH> disableEmptyLayout();

    ConfigurationBuilder<T, VH> dataBindingByEmptyLayout(DataBinder<T, VH> dataBinder);

    ConfigurationBuilder<T, VH> createEmptyLayoutItemView(ItemViewFactory itemViewFactory);

    ConfigurationBuilder<T, VH> createEmptyLayoutItemView(@LayoutRes int layoutResId);


    ConfigurationBuilder<T, VH> setItemCallback(DiffUtil.ItemCallback<T> itemCallback);

    ConfigurationBuilder<T, VH> setDiffDispatcher(IDiffDispatcher<T, VH> diffDispatcher);

    ConfigurationBuilder<T, VH> setSpanSizeLookup(SpanSizeLookup<T> spanSizeLookup);

    ConfigurationBuilder<T, VH> setAdapterFactory(AdapterFactory<T, VH> adapterFactory);

    ConfigurationBuilder<T, VH> setLayoutManagerFactory(LayoutManagerFactory layoutManagerFactory);

    ConfigurationBuilder<T, VH> setViewBindHelper(ViewBindHelper<T, VH> viewBindHelper);

    ConfigurationBuilder<T, VH> setItemDataUpdateHelper(ItemDataUpdateHelper<T, VH> itemDataUpdateHelper);

    ConfigurationBuilder<T, VH> setAdapterDataObserverFactory(AdapterDataObserverFactory<T, VH> adapterDataObserverFactory);

    ConfigurationBuilder<T, VH> setDataProvider(DataProvider<T, VH> dataProvider);

    ConfigurationBuilder<T, VH> setDataClassifier(DataClassifier<T, VH> dataClassifier);

    ConfigurationBuilder<T, VH> setItemDataHolderFactory(ItemDataHolderFactory<T, VH> itemDataHolderFactory);

    ConfigurationBuilder<T, VH> setListenerManager(ListenerManager<T, VH> listenerManager);

    ConfigurationBuilder<T, VH> createItemView(@LayoutRes int layoutId, int... itemTypes);

    ConfigurationBuilder<T, VH> createItemView(ItemViewFactory itemViewFactory, int... itemTypes);

    ConfigurationBuilder<T, VH> createItemView(ItemViewFactory itemViewFactory, ItemBindingMatchPolicy matchPolicy);

    ConfigurationBuilder<T, VH> updateItemData(ItemDataUpdate<T, VH> itemDataUpdate);

    ConfigurationBuilder<T, VH> dataBinding(DataBinder<T, VH> dataBinder, DataBindingMatchPolicy matchPolicy);

    ConfigurationBuilder<T, VH> dataBindingByItemTypes(DataBinder<T, VH> dataBinder, int... itemTypes);

    ConfigurationBuilder<T, VH> dataBindingByPayloads(DataBinder<T, VH> dataBinder, Object... payloads);

    ConfigurationBuilder<T, VH> dataBindingByPayloadsOrNotIncludedPayloads(DataBinder<T, VH> dataBinder, Object... payloads);

    ConfigurationBuilder<T, VH> addOnAttachedToRecyclerViewListener(OnAttachedToRecyclerViewListener<T, VH> attachedToRecyclerViewListener);

    ConfigurationBuilder<T, VH> addOnCreatedViewHolderListener(OnCreatedViewHolderListener<T, VH> createdViewHolderListener);

    ConfigurationBuilder<T, VH> addOnDetachedFromRecyclerViewListener(OnDetachedFromRecyclerViewListener<T, VH> detachedFromRecyclerViewListener);

    ConfigurationBuilder<T, VH> addOnViewAttachedToWindowListener(OnViewAttachedToWindowListener<T, VH> viewAttachedToWindowListener);

    ConfigurationBuilder<T, VH> addOnViewDetachedFromWindowListener(OnViewDetachedFromWindowListener<T, VH> viewDetachedFromWindowListener);

    ConfigurationBuilder<T, VH> addOnViewRecycledListener(OnViewRecycledListener<T, VH> viewRecycledListener);

    ConfigurationBuilder<T, VH> addOnItemClickListener(OnItemClickListener<T, VH> itemClickListener, DataBindingMatchPolicy matchPolicy);

    ConfigurationBuilder<T, VH> addOnItemLongClickListener(OnItemLongClickListener<T, VH> itemLongClickListener, DataBindingMatchPolicy matchPolicy);

    ConfigurationBuilder<T, VH> setExecutorService(ExecutorService executorService);

    ConfigurationBuilder<T, VH> setMainHandler(Handler mainHandler);

    ConfigurationBuilder<T, VH> setCurrentActivity(Activity currentActivity);

    ConfigurationBuilder<T, VH> component(Component<T, VH> component);

    ConfigurationBuilder<T, VH> plugin(Plugin<T, VH> plugin);

    RecyclerViewConfiguration<T, VH> build(RecyclerView recyclerView);

    ViewPagerConfiguration<T, VH> build(ViewPager2 viewPager2);

}
