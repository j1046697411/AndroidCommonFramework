package org.jzl.android.recyclerview.core.configuration;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.LayoutManagerHelper;
import org.jzl.android.recyclerview.core.data.DataClassifier;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.core.diff.IDiffDispatcher;
import org.jzl.android.recyclerview.core.factory.ItemDataHolderFactory;
import org.jzl.android.recyclerview.core.factory.ItemViewFactory;
import org.jzl.android.recyclerview.core.factory.LayoutManagerFactory;
import org.jzl.android.recyclerview.core.item.ItemDataUpdate;
import org.jzl.android.recyclerview.core.item.ItemDataUpdateHelper;
import org.jzl.android.recyclerview.core.observer.AdapterDataObservable;
import org.jzl.android.recyclerview.core.vh.CommonlyViewHolder;
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
import org.jzl.lang.util.holder.BinaryHolder;

import java.util.concurrent.ExecutorService;

public interface Configuration<T, VH extends RecyclerView.ViewHolder> {

    static <T, VH extends RecyclerView.ViewHolder> ConfigurationBuilder<T, VH> builder(ViewHolderFactory<VH> viewHolderFactory) {
        return new ConfigurationBuilderImpl<>(viewHolderFactory);
    }

    static <T> ConfigurationBuilder<T, CommonlyViewHolder> builder() {
        return builder((itemView, viewType) -> new CommonlyViewHolder(itemView));
    }

    Activity getCurrentActivity();

    Handler getMainHandler();

    ExecutorService getExecutorService();

    GroupManager<T, VH> getGroupManager();

    ItemDataHolderFactory<T, VH> getItemDataHolderFactory();

    DataProvider<T, VH> getDataProvider();

    DataClassifier<T, VH> getDataClassifier();

    LayoutManagerFactory getLayoutManagerFactory();

    ViewHolderFactory<VH> getViewHolderFactory();

    IDiffDispatcher<T, VH> getDiffDispatcher();

    LayoutInflater getLayoutInflater();

    ItemViewFactory matchItemViewFactory(int viewType);

    ViewBindHelper<T, VH> getViewBindHelper();

    ItemDataUpdateHelper<T, VH> getItemDataUpdateHelper();

    ListenerManager<T, VH> getListenerManager();

    AdapterDataObservable getAdapterDataObservable();

    LayoutManagerHelper<T, VH> getLayoutManagerHelper();

    Iterable<ItemDataUpdate<T, VH>> getItemDataUpdates();

    Iterable<Component<T, VH>> getComponents();

    EmptyLayoutManager<T, VH> getEmptyLayoutManager();

    Iterable<BinaryHolder<DataBindingMatchPolicy, DataBinder<T, VH>>> getDataBinders();

    Iterable<OnAttachedToRecyclerViewListener<T, VH>> getOnAttachedToRecyclerViewListeners();

    Iterable<OnCreatedViewHolderListener<T, VH>> getOnCreatedViewHolderListeners();

    Iterable<OnDetachedFromRecyclerViewListener<T, VH>> getOnDetachedFromRecyclerViewListeners();

    Iterable<OnViewAttachedToWindowListener<T, VH>> getOnViewAttachedToWindowListeners();

    Iterable<OnViewDetachedFromWindowListener<T, VH>> getOnViewDetachedFromWindowListeners();

    Iterable<OnViewRecycledListener<T, VH>> getOnViewRecycledListeners();

    Iterable<BinaryHolder<DataBindingMatchPolicy, OnItemClickListener<T, VH>>> getOnItemClickListeners();

    Iterable<BinaryHolder<DataBindingMatchPolicy, OnItemLongClickListener<T, VH>>> getOnItemLongClickListeners();

    <C extends Component<T, VH>> C getComponent(Class<C> type);

    Configuration<T, VH> addOnAttachedToRecyclerViewListener(OnAttachedToRecyclerViewListener<T, VH> attachedToRecyclerViewListener);

    Configuration<T, VH> addOnCreatedViewHolderListener(OnCreatedViewHolderListener<T, VH> createdViewHolderListener);

    Configuration<T, VH> addOnDetachedFromRecyclerViewListener(OnDetachedFromRecyclerViewListener<T, VH> detachedFromRecyclerViewListener);

    Configuration<T, VH> addOnViewAttachedToWindowListener(OnViewAttachedToWindowListener<T, VH> viewAttachedToWindowListener);

    Configuration<T, VH> addOnViewDetachedFromWindowListener(OnViewDetachedFromWindowListener<T, VH> viewDetachedFromWindowListener);

    Configuration<T, VH> addOnViewRecycledListener(OnViewRecycledListener<T, VH> viewRecycledListener);

    Configuration<T, VH> addOnItemClickListener(OnItemClickListener<T, VH> itemClickListener, DataBindingMatchPolicy matchPolicy);

    Configuration<T, VH> addOnItemLongClickListener(OnItemLongClickListener<T, VH> itemLongClickListener, DataBindingMatchPolicy matchPolicy);

}
