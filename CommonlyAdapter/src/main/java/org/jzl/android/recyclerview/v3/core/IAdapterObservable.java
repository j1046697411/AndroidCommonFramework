package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IAdapterObservable<T, VH extends IViewHolder> {

    void observe(@NonNull IObserver observer);

    void unobserved(@NonNull IObserver observer);

    void observe(@NonNull IDataChangedObserver<T, VH> observer);

    void unobserved(@NonNull IDataChangedObserver<T, VH> observer);

    void enableNotify();

    void disableNotify();

    void notifyDataSetChanged();

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload);

    void notifyItemRangeInserted(int positionStart, int itemCount);

    void notifyItemRangeRemoved(int positionStart, int itemCount);

    void notifyItemMoved(int fromPosition, int toPosition);

    interface IObserver {

        void onDataSetChanged();

        void onItemRangeChanged(int positionStart, int itemCount);

        default void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            onItemRangeChanged(positionStart, itemCount);
        }

        void onItemRangeInserted(int positionStart, int itemCount);

        void onItemRangeRemoved(int positionStart, int itemCount);

        void onItemRangeMoved(int fromPosition, int toPosition, int itemCount);
    }

    interface IDataChangedObserver<T, VH extends IViewHolder> {

        default void onBeforeDataChanged(@NonNull IConfiguration<T, VH> configuration, @NonNull IAdapterObservable<T, VH> adapterObservable, @NonNull Event event) {
        }

        default void onAfterDataChanged(@NonNull IConfiguration<T, VH> configuration, @NonNull IAdapterObservable<T, VH> adapterObservable, @NonNull Event event) {
        }
    }

    enum Event {

        CHANGED, INSERTED, REMOVED, MOVED

    }

}
