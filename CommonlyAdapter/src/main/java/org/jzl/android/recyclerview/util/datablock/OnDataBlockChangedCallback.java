package org.jzl.android.recyclerview.util.datablock;

public interface OnDataBlockChangedCallback<T, D extends DataBlockProvider<T>> {

    void onChanged(D sender);

    void onItemRangeChanged(D sender, int positionStart, int itemCount);

    void onItemRangeInserted(D sender, int positionStart, int itemCount);

    void onItemRangeMoved(D sender, int fromPosition, int toPosition);

    void onItemRangeRemoved(D sender, int positionStart, int itemCount);
}