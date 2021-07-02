package org.jzl.android.recyclerview.core.diff;

import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.core.observer.AdapterDataObservable;
import org.jzl.lang.util.CollectionUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class AsyncDiffDispatcher<T, VH extends RecyclerView.ViewHolder> implements IDiffDispatcher<T, VH> {

    private DiffUtil.ItemCallback<T> itemCallback;
    private DiffListUpdateCallback diffListUpdateCallback;
    private DataProvider<T, VH> dataProvider;

    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        executorService = configuration.getExecutorService();
        mainHandler = configuration.getMainHandler();
        this.diffListUpdateCallback = new DiffListUpdateCallback(configuration.getAdapterDataObservable());
        this.dataProvider = configuration.getDataProvider();
    }

    @Override
    public void submitList(List<T> newList) {
        if (CollectionUtils.isEmpty(newList) || CollectionUtils.isEmpty(dataProvider)) {
            replaceAll(dataProvider, newList, true);
            return;
        }
        List<T> oldList = dataProvider;
        calculateDiff(oldList, newList);
    }

    private void calculateDiff(List<T> oldList, List<T> newList) {
        executorService.submit(() -> {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return oldList.size();
                }

                @Override
                public int getNewListSize() {
                    return newList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    T oldItem = oldList.get(oldItemPosition);
                    T newItem = newList.get(newItemPosition);
                    if (ObjectUtils.nonNull(oldItem) && ObjectUtils.nonNull(newItem)) {
                        return itemCallback.areItemsTheSame(oldItem, newItem);
                    }
                    return ObjectUtils.isNull(oldItem) && ObjectUtils.isNull(newItem);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    T oldItem = oldList.get(oldItemPosition);
                    T newItem = newList.get(newItemPosition);
                    if (ObjectUtils.nonNull(oldItem) && ObjectUtils.nonNull(newItem)) {
                        return itemCallback.areContentsTheSame(oldItem, newItem);
                    }
                    return ObjectUtils.isNull(oldItem) && ObjectUtils.isNull(newItem);
                }

                @Nullable
                @Override
                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                    T oldItem = oldList.get(oldItemPosition);
                    T newItem = newList.get(newItemPosition);
                    if (ObjectUtils.nonNull(oldItem) && ObjectUtils.nonNull(newItem)) {
                        return itemCallback.getChangePayload(oldItem, newItem);
                    }
                    return null;
                }
            });
            mainHandler.post(() -> dispatchUpdatesTo(newList, diffResult));
        });
    }

    @SuppressWarnings("unchecked")
    private void replaceAll(DataProvider<T, ?> dataProvider, List<T> newList, boolean notify) {
        if (dataProvider instanceof IDiffDispatcherUpdatable) {
            ((IDiffDispatcherUpdatable<T>) dataProvider).replaceAll(newList, notify);
        } else {
            if (CollectionUtils.nonEmpty(dataProvider)) {
                dataProvider.clear();
            }
            if (CollectionUtils.nonEmpty(newList)) {
                dataProvider.addAll(newList);
            }
        }
    }

    private void dispatchUpdatesTo(List<T> newList, DiffUtil.DiffResult result) {
        replaceAll(dataProvider, newList, false);
        result.dispatchUpdatesTo(diffListUpdateCallback);
    }

    @Override
    public IDiffDispatcher<T, VH> setItemCallback(DiffUtil.ItemCallback<T> itemCallback) {
        this.itemCallback = itemCallback;
        return this;
    }

    private static class DiffListUpdateCallback implements ListUpdateCallback {

        private final AdapterDataObservable adapterDataObservable;

        public DiffListUpdateCallback(AdapterDataObservable adapterDataObservable) {
            this.adapterDataObservable = adapterDataObservable;
        }

        @Override
        public void onInserted(int position, int count) {
            adapterDataObservable.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            adapterDataObservable.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            adapterDataObservable.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, @Nullable Object payload) {
            adapterDataObservable.notifyItemRangeChanged(position, count, payload);
        }
    }

}
