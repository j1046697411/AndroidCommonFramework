package org.jzl.android.recyclerview.util.datablock;

public interface DataBlockObserver<T, D extends DataBlockProvider<T>> {

    void enableDataChangedNotify();

    void disableDataChangedNotify();

    void addOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, D> dataBlockChangedCallback);

    void removeOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, D> dataBlockChangedCallback);

    void notifyDataRangeChanged(int positionStart, int itemCount);

    void notifyDataRangeInserted(int positionStart, int itemCount);

    void notifyDataRangeMoved(int fromPosition, int toPosition);

    void notifyDataRangeRemoved(int positionStart, int itemCount);

    void notifyDataChanged();
}
