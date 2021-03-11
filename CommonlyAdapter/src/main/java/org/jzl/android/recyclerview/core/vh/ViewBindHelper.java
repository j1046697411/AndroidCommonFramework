package org.jzl.android.recyclerview.core.vh;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;

public interface ViewBindHelper<T, VH extends RecyclerView.ViewHolder> extends Component<T, VH> , IViewHolder{

    void onBindViewHolder(ItemDataHolder<T, VH> dataHolder, VH viewHolder);

}
