package org.jzl.android.recyclerview.manager.layout;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.factory.ItemViewFactory;
import org.jzl.android.recyclerview.core.vh.DataBinder;
import org.jzl.android.recyclerview.manager.Manager;

public interface EmptyLayoutManager<T, VH extends RecyclerView.ViewHolder> extends Manager<T, VH> {

    int TYPE_EMPTY_LAYOUT = -1;

    T getEmptyData();

    EmptyLayoutManager<T, VH> setEmptyData(T data);

    int getItemType();

    boolean isEnable();

    EmptyLayoutManager<T, VH> createItemView(ItemViewFactory itemViewFactory);

    EmptyLayoutManager<T, VH> createItemView(@LayoutRes int layoutResId);

    EmptyLayoutManager<T, VH> dataBinding(DataBinder<T, VH> dataBinder);

    EmptyLayoutManager<T, VH> enable();

    EmptyLayoutManager<T, VH> disable();

    EmptyLayoutManager<T, VH> setEmptyItemType(int itemType);

    EmptyLayoutManager<T, VH> addEmptyLayoutCallback(EmptyLayoutCallback emptyLayoutCallback);

    EmptyLayoutManager<T, VH> removeEmptyLayoutCallback(EmptyLayoutCallback emptyLayoutCallback);

    interface EmptyLayoutCallback {

        void onEmptyLayoutStateChanged(boolean isEmpty);

    }

}
