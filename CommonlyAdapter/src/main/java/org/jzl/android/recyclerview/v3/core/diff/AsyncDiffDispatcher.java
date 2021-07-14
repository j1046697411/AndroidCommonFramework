package org.jzl.android.recyclerview.v3.core.diff;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;

import org.jzl.android.recyclerview.v3.core.IAdapterObservable;
import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IConfigurationBuilder;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.components.IComponent;
import org.jzl.lang.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

class AsyncDiffDispatcher<T, VH extends IViewHolder> implements IDiffDispatcher<T, VH>, IComponent<T, VH> {

    private IConfiguration<T, VH> configuration;
    private DiffListUpdateCallback diffListUpdateCallback;
    private ExecutorService executorService;
    private Handler mainHandler;
    private final IItemCallback<T> itemCallback;
    private final AtomicInteger mMaxScheduledGeneration = new AtomicInteger(0);
    private IAdapterObservable<T, VH> adapterObservable;

    public AsyncDiffDispatcher(@NonNull IItemCallback<T> itemCallback) {
        this.itemCallback = itemCallback;
    }

    @Override
    public void submitList(List<T> newList) {
        List<T> oldList = configuration.getDataProvider();
        if (newList == oldList) {
            return;
        }
        if (CollectionUtils.isEmpty(newList)) {
            int size = oldList.size();
            replaceAll(oldList, Collections.emptyList());
            adapterObservable.notifyItemRangeRemoved(0, size);
            return;
        }
        if (CollectionUtils.isEmpty(oldList)) {
            int size = newList.size();
            replaceAll(oldList, newList);
            adapterObservable.notifyItemRangeInserted(0, size);
            return;
        }
        int currentScheduledGeneration = mMaxScheduledGeneration.incrementAndGet();
        executorService.submit(() -> {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
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
                    return itemCallback.areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return itemCallback.areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
                }

                @Nullable
                @Override
                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                    return itemCallback.getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
                }
            });
            mainHandler.post(() -> {
                if (currentScheduledGeneration == mMaxScheduledGeneration.get()) {
                    replaceAll(oldList, newList);
                    result.dispatchUpdatesTo(diffListUpdateCallback);
                }
            });
        });
    }

    private void replaceAll(List<T> oldList, List<T> newList) {
        adapterObservable.disableNotify(); //防止移除数据时候已经通知更新数据了
        if (CollectionUtils.nonEmpty(oldList)) {
            oldList.clear();
        }
        if (CollectionUtils.nonEmpty(newList)) {
            oldList.addAll(newList);
        }
        adapterObservable.enableNotify();
    }

    @Override
    public void setup(@NonNull IConfigurationBuilder<T, VH> builder) {
        builder.addComponent(this);
    }

    @Override
    public void initialize(IConfiguration<T, VH> configuration) {
        this.configuration = configuration;
        this.adapterObservable = configuration.getAdapterObservable();
        this.diffListUpdateCallback = new DiffListUpdateCallback(adapterObservable);
        this.executorService = configuration.getExecutorService();
        this.mainHandler = configuration.getMainHandler();
    }

    class DiffListUpdateCallback implements ListUpdateCallback {

        private final IAdapterObservable<T, VH> adapterObservable;

        DiffListUpdateCallback(IAdapterObservable<T, VH> adapterObservable) {
            this.adapterObservable = adapterObservable;
        }

        @Override
        public void onInserted(int position, int count) {
            adapterObservable.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            adapterObservable.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            adapterObservable.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, @Nullable Object payload) {
            adapterObservable.notifyItemRangeChanged(position, count, payload);
        }
    }
}
