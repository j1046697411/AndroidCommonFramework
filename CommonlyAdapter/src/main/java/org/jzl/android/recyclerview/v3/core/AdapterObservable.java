package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.util.Logger;
import org.jzl.lang.util.ForeachUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class AdapterObservable<T, VH extends IViewHolder> implements IAdapterObservable<T, VH> {
    private static final Logger log = Logger.logger(AdapterObservable.class);

    @NonNull
    private final IConfiguration<T, VH> configuration;
    @NonNull
    private final RecyclerView.Adapter<?> adapter;

    private final List<IObserver> observers = new CopyOnWriteArrayList<>();
    private final List<IDataChangedObserver<T, VH>> dataChangedObservers = new CopyOnWriteArrayList<>();
    private int semaphore = 0;

    AdapterObservable(@NonNull IConfiguration<T, VH> configuration, @NonNull RecyclerView.Adapter<?> adapter) {
        this.configuration = configuration;
        this.adapter = adapter;
        adapter.registerAdapterDataObserver(new AdapterDataObserver());
    }

    @Override
    public void observe(@NonNull IObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unobserved(@NonNull IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void observe(@NonNull IDataChangedObserver<T, VH> observer) {
        if (!dataChangedObservers.contains(observer)) {
            dataChangedObservers.add(observer);
        }
    }

    @Override
    public void unobserved(@NonNull IDataChangedObserver<T, VH> observer) {
        dataChangedObservers.remove(observer);
    }

    @Override
    public void enableNotify() {
        semaphore++;
    }

    @Override
    public void disableNotify() {
        semaphore--;
    }

    @Override
    public void notifyDataSetChanged() {
        if (semaphore == 0) {
            notifyBeforeDataChanged(Event.CHANGED);
            adapter.notifyDataSetChanged();
            notifyAfterDataChanged(Event.CHANGED);
        }
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        if (semaphore == 0) {
            notifyBeforeDataChanged(Event.CHANGED);
            adapter.notifyItemRangeChanged(positionStart, itemCount);
            notifyAfterDataChanged(Event.CHANGED);
        }
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        if (semaphore == 0) {
            notifyBeforeDataChanged(Event.CHANGED);
            adapter.notifyItemRangeChanged(positionStart, itemCount, payload);
            notifyAfterDataChanged(Event.CHANGED);
        }
    }

    @Override
    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        if (semaphore == 0) {
            notifyBeforeDataChanged(Event.INSERTED);
            adapter.notifyItemRangeInserted(positionStart, itemCount);
            notifyAfterDataChanged(Event.INSERTED);
        }
    }

    @Override
    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        if (semaphore == 0) {
            notifyBeforeDataChanged(Event.REMOVED);
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
            notifyAfterDataChanged(Event.REMOVED);
        }
    }

    @Override
    public void notifyItemMoved(int fromPosition, int toPosition) {
        if (semaphore == 0) {
            notifyBeforeDataChanged(Event.MOVED);
            adapter.notifyItemMoved(fromPosition, toPosition);
            notifyAfterDataChanged(Event.MOVED);
        }
    }

    private void notifyBeforeDataChanged(@NonNull Event event) {
        ForeachUtils.each(this.dataChangedObservers, target -> target.onBeforeDataChanged(configuration, this, event));
    }

    private void notifyAfterDataChanged(@NonNull Event event) {
        ForeachUtils.each(this.dataChangedObservers, target -> target.onAfterDataChanged(configuration, this, event));
    }

    private class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            log.i("onChanged");
            ForeachUtils.each(observers, IObserver::onDataSetChanged);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            log.i("onItemRangeChanged => " + positionStart + "," + itemCount);
            ForeachUtils.each(observers, target -> target.onItemRangeChanged(positionStart, itemCount));
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            log.i("onItemRangeChanged => " + positionStart + "," + itemCount + "|" + payload);
            ForeachUtils.each(observers, target -> target.onItemRangeChanged(positionStart, itemCount, payload));
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            log.i("onItemRangeInserted => " + positionStart + "," + itemCount);
            ForeachUtils.each(observers, target -> target.onItemRangeInserted(positionStart, itemCount));
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            log.i("onItemRangeRemoved => " + positionStart + "," + itemCount);
            ForeachUtils.each(observers, target -> target.onItemRangeRemoved(positionStart, itemCount));
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            log.i("onItemRangeMoved => " + fromPosition + "," + toPosition);
            ForeachUtils.each(observers, target -> target.onItemRangeMoved(fromPosition, toPosition, itemCount));
        }
    }

}
