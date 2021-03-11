package org.jzl.android.recyclerview.manager.layout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.configuration.ConfigurationBuilder;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.core.factory.ItemViewFactory;
import org.jzl.android.recyclerview.core.observer.AdapterDataObserver;
import org.jzl.android.recyclerview.core.vh.CompositeDataBinder;
import org.jzl.android.recyclerview.core.vh.DataBinder;
import org.jzl.android.recyclerview.manager.AbstractManager;
import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.PositionType;
import org.jzl.lang.util.CollectionUtils;

import java.util.Set;

public class DefaultEmptyLayoutManager<T, VH extends RecyclerView.ViewHolder> extends AbstractManager<T, VH> implements EmptyLayoutManager<T, VH> {

    private boolean enable = false;
    private T data;
    private int itemType = TYPE_EMPTY_LAYOUT;
    private final CompositeDataBinder<T, VH> dataBinders = CompositeDataBinder.of();
    private boolean isInitialise = false;
    private ItemViewFactory itemViewFactory;
    private DataProvider<T, VH> dataProvider;
    private boolean isEmpty = true;
    private final CompositeEmptyLayoutCallback compositeEmptyLayoutCallback = new CompositeEmptyLayoutCallback();

    @Override
    public void setup(ConfigurationBuilder<T, VH> builder) {
        super.setup(builder);
        if (itemViewFactory != null) {
            builder.createItemView(itemViewFactory, itemType);
        }
        builder.dataBindingByItemTypes(dataBinders, itemType);
        isInitialise = true;
    }

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        this.dataProvider = configuration.getDataProvider();
        if (isEnable()) {
            configuration.getAdapterDataObservable().registerAdapterDataObserver(new EmptyLayoutManagerAdapterDataObserver());
        }
    }

    public EmptyLayoutManager<T, VH> dataBinding(DataBinder<T, VH> dataBinder) {
        this.dataBinders.addDataBinder(dataBinder);
        return this;
    }

    @Override
    public EmptyLayoutManager<T, VH> enable() {
        enable = true;
        return this;
    }

    @Override
    public EmptyLayoutManager<T, VH> disable() {
        enable = false;
        return this;
    }

    @Override
    public EmptyLayoutManager<T, VH> setEmptyItemType(int itemType) {
        if (isInitialise) {
            throw new RuntimeException("");
        }
        this.itemType = itemType;
        return this;
    }

    @Override
    public EmptyLayoutManager<T, VH> setEmptyData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public EmptyLayoutManager<T, VH> addEmptyLayoutCallback(EmptyLayoutCallback emptyLayoutCallback) {
        compositeEmptyLayoutCallback.addEmptyLayoutCallback(emptyLayoutCallback);
        return this;
    }

    @Override
    public EmptyLayoutManager<T, VH> removeEmptyLayoutCallback(EmptyLayoutCallback emptyLayoutCallback) {
        compositeEmptyLayoutCallback.removeEmptyLayoutCallback(emptyLayoutCallback);
        return this;
    }

    @Override
    public T getEmptyData() {
        return data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public EmptyLayoutManager<T, VH> createItemView(ItemViewFactory itemViewFactory) {
        if (isInitialise) {
            throw new RuntimeException("");
        }
        this.itemViewFactory = itemViewFactory;
        return this;
    }

    @Override
    public EmptyLayoutManager<T, VH> createItemView(@LayoutRes int layoutResId) {
        return createItemView((layoutInflater, parent, viewType) -> layoutInflater.inflate(layoutResId, parent, false));
    }

    private void notifyEmptyLayoutChanged() {
        boolean isEmpty = isEmpty(dataProvider);
        if (isEmpty != this.isEmpty) {
            onEmptyLayoutStateChanged(isEmpty);
            this.isEmpty = isEmpty;
        }
    }

    @SuppressWarnings("unchecked")
    private boolean isEmpty(DataProvider<T, VH> dataProvider) {
        if (dataProvider instanceof DataBlockProvider) {
            Set<DataBlock<T>> dataBlocks = ((DataBlockProvider<T>) dataProvider).findDataBlockByPositionType(PositionType.CONTENT);
            if (CollectionUtils.isEmpty(dataBlocks)) {
                return true;
            }
            for (DataBlock<T> dataBlock : dataBlocks) {
                if (CollectionUtils.nonEmpty(dataBlock)) {
                    return false;
                }
            }
            return true;
        } else {
            return dataProvider.isEmpty();
        }
    }

    private void onEmptyLayoutStateChanged(boolean isEmpty) {
        compositeEmptyLayoutCallback.onEmptyLayoutStateChanged(isEmpty);
    }

    private class EmptyLayoutManagerAdapterDataObserver implements AdapterDataObserver {

        @Override
        public void onChanged() {
            notifyEmptyLayoutChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyEmptyLayoutChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            notifyEmptyLayoutChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyEmptyLayoutChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyEmptyLayoutChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyEmptyLayoutChanged();
        }
    }
}
