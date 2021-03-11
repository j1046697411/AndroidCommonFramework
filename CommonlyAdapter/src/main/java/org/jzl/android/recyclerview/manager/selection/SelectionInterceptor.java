package org.jzl.android.recyclerview.manager.selection;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.data.Selectable;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;

public interface SelectionInterceptor<T extends Selectable, VH extends RecyclerView.ViewHolder> {

    void intercept(ItemDataHolder<T, VH> dataHolder, VH viewHolder, Selector<T> selector);

}