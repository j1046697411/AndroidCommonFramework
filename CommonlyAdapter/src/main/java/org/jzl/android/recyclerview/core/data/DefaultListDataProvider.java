package org.jzl.android.recyclerview.core.data;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.diff.IDiffDispatcherUpdatable;
import org.jzl.android.recyclerview.core.observer.AdapterDataObservable;
import org.jzl.android.recyclerview.manager.layout.EmptyLayoutManager;
import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockObserver;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.DataBlockProviders;
import org.jzl.android.recyclerview.util.datablock.OnDataBlockChangedCallback;
import org.jzl.android.recyclerview.util.datablock.PositionType;
import org.jzl.android.recyclerview.util.datablock.impl.AbstractDataSource;
import org.jzl.lang.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DefaultListDataProvider<T, VH extends RecyclerView.ViewHolder> extends AbstractDataSource<T> implements DataProvider<T, VH>, DataBlockProvider<T>, IDiffDispatcherUpdatable<T> {

    private final DataBlockProvider<T> dataBlockProvider = DataBlockProviders.dataBlockProvider();

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        this.dataBlockProvider.addOnDataBlockChangedCallback(new UpdateDataBlockChangedCallback<>(configuration.getAdapterDataObservable(), configuration.getEmptyLayoutManager()));
    }

    @Override
    protected List<T> proxy() {
        return dataBlockProvider;
    }

    @Override
    public void dirty() {
        dataBlockProvider.dirty();
    }

    @Override
    @NonNull
    public Collection<DataBlock<T>> getDataBlocks() {
        return dataBlockProvider.getDataBlocks();
    }

    @Override
    @NonNull
    public DataBlockObserver<T, DataBlockProvider<T>> getDataBlockObserver() {
        return dataBlockProvider.getDataBlockObserver();
    }

    @Override
    public void addOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, DataBlockProvider<T>> dataBlockChangedCallback) {
        dataBlockProvider.addOnDataBlockChangedCallback(dataBlockChangedCallback);
    }

    @Override
    public void removeOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, DataBlockProvider<T>> dataBlockChangedCallback) {
        dataBlockProvider.removeOnDataBlockChangedCallback(dataBlockChangedCallback);
    }

    @Override
    public void addDataBlock(@NonNull DataBlock<T> dataBlock) {
        dataBlockProvider.addDataBlock(dataBlock);
    }

    @Override
    public void addDataBlockAll(@NonNull Collection<DataBlock<T>> dataBlocks) {
        dataBlockProvider.addDataBlockAll(dataBlocks);
    }

    @Override
    public void removeDataBlock(@NonNull DataBlock<T> dataBlock) {
        dataBlockProvider.removeDataBlock(dataBlock);
    }

    @Override
    public void removeDataBlockAll(@NonNull Collection<DataBlock<T>> dataBlocks) {
        dataBlockProvider.removeDataBlockAll(dataBlocks);
    }

    @Override
    public DataBlock<T> dataBlock(PositionType positionType, int dataBlockId) {
        return dataBlockProvider.dataBlock(positionType, dataBlockId);
    }

    @Override
    public DataBlock<T> defaultDataBlock() {
        return dataBlockProvider.defaultDataBlock();
    }

    @Override
    public DataBlock<T> lastDataBlock() {
        return dataBlockProvider.lastDataBlock();
    }

    @Override
    public DataBlock<T> lastContentDataBlock() {
        return dataBlockProvider.lastContentDataBlock();
    }

    @Override
    public Set<DataBlock<T>> findDataBlockByPositionType(PositionType positionType) {
        return dataBlockProvider.findDataBlockByPositionType(positionType);
    }

    @Override
    public DataBlock<T> findDataBlockByPositionTypeAndDataBlockId(PositionType positionType, int dataBlockId) {
        return dataBlockProvider.findDataBlockByPositionTypeAndDataBlockId(positionType, dataBlockId);
    }

    @Override
    public DataBlock<T> findDataBlockByPosition(int position) {
        return dataBlockProvider.findDataBlockByPosition(position);
    }

    @Override
    public void replaceAll(List<T> newData, boolean notify) {
        DataBlockObserver<T, DataBlockProvider<T>> dataBlockObserver = dataBlockProvider.getDataBlockObserver();
        if (!notify) {
            dataBlockObserver.disableDataChangedNotify();
        }
        replaceAll(newData);
        if (!notify) {
            dataBlockObserver.enableDataChangedNotify();
        }
    }

    @Override
    public void replaceAll(List<T> newData) {
        if (CollectionUtils.nonEmpty(dataBlockProvider)) {
            dataBlockProvider.clear();
        }
        if (CollectionUtils.nonEmpty(newData)) {
            dataBlockProvider.addAll(newData);
        }
    }

    private static class UpdateDataBlockChangedCallback<T> implements OnDataBlockChangedCallback<T, DataBlockProvider<T>> {

        private final AdapterDataObservable adapterDataObservable;
        private final EmptyLayoutManager<T, ?> emptyLayoutManager;
        private boolean isEmptyData = true;


        public UpdateDataBlockChangedCallback(AdapterDataObservable adapterDataObservable, EmptyLayoutManager<T, ?> emptyLayoutManager) {
            this.adapterDataObservable = adapterDataObservable;
            this.emptyLayoutManager = emptyLayoutManager;
        }

        @Override
        public void onChanged(DataBlockProvider<T> sender) {
            this.adapterDataObservable.notifyDataSetChanged();
            this.isEmptyData = sender.isEmpty();
        }

        @Override
        public void onItemRangeChanged(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            this.adapterDataObservable.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            if (isEmptyData && emptyLayoutManager.isEnable()) {
                this.adapterDataObservable.notifyItemRemoved(0);
            }
            this.adapterDataObservable.notifyItemRangeInserted(positionStart, itemCount);
            this.isEmptyData = sender.isEmpty();
        }

        @Override
        public void onItemRangeMoved(DataBlockProvider<T> sender, int fromPosition, int toPosition) {
            this.adapterDataObservable.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeRemoved(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            this.adapterDataObservable.notifyItemRangeRemoved(positionStart, itemCount);
            this.isEmptyData = sender.isEmpty();
            if (isEmptyData && emptyLayoutManager.isEnable()) {
                this.adapterDataObservable.notifyItemRangeInserted(0, 1);
            }

        }
    }

}
