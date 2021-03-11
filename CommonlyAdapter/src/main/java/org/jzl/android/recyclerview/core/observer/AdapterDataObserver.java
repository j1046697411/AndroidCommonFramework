package org.jzl.android.recyclerview.core.observer;

import androidx.annotation.Nullable;

public interface AdapterDataObserver {

    void onChanged();

    void onItemRangeChanged(int positionStart, int itemCount);

    void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload);

    void onItemRangeInserted(int positionStart, int itemCount);

    void onItemRangeRemoved(int positionStart, int itemCount);

    void onItemRangeMoved(int fromPosition, int toPosition, int itemCount);
}
