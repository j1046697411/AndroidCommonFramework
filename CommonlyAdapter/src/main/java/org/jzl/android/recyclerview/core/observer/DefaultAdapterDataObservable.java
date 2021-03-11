package org.jzl.android.recyclerview.core.observer;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultAdapterDataObservable implements AdapterDataObservable {

    private final RecyclerView.Adapter<?> adapter;
    private final CompositeAdapterDataObserver adapterDataObserver = new CompositeAdapterDataObserver();

    public DefaultAdapterDataObservable(RecyclerView.Adapter<?> adapter) {
        this.adapter = adapter;
        this.adapter.registerAdapterDataObserver(adapterDataObserver);
    }

    @Override
    public void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
        this.adapterDataObserver.registerAdapterDataObserver(adapterDataObserver);
    }

    @Override
    public void unregisterAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
        this.adapterDataObserver.unregisterAdapterDataObserver(adapterDataObserver);
    }

    @Override
    public void notifyObtainSnapshot() {
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void notifyItemChanged(int position, @Nullable Object payload) {
        adapter.notifyItemChanged(position, payload);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        adapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        adapter.notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    @Override
    public void notifyItemInserted(int position) {
        adapter.notifyItemInserted(position);
    }

    @Override
    public void notifyItemMoved(int fromPosition, int toPosition) {
        adapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        adapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void notifyItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        adapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    private static class CompositeAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        private final CopyOnWriteArrayList<AdapterDataObserver> observers = new CopyOnWriteArrayList<>();

        public CompositeAdapterDataObserver() {
            super();
        }

        public void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            if (ObjectUtils.nonNull(adapterDataObserver)) {
                observers.add(adapterDataObserver);
            }
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            observers.remove(adapterDataObserver);
        }

        @Override
        public void onChanged() {
            super.onChanged();
            ForeachUtils.each(observers, AdapterDataObserver::onChanged);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            ForeachUtils.each(observers, target -> target.onItemRangeChanged(positionStart, itemCount));
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            ForeachUtils.each(observers, target -> target.onItemRangeChanged(positionStart, itemCount, payload));
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            ForeachUtils.each(observers, target -> target.onItemRangeInserted(positionStart, itemCount));
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            ForeachUtils.each(observers, target -> target.onItemRangeRemoved(positionStart, itemCount));
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            ForeachUtils.each(observers, target -> target.onItemRangeMoved(fromPosition, toPosition, itemCount));
        }
    }
}
