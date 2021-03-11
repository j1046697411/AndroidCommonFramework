package org.jzl.android.recyclerview.core.item;

import java.util.List;

public interface ItemContext {

    int getAdapterPosition();

    int getItemViewType();

    long getItemId();

    List<Object> getPayloads();

    <E> E getExtra(int key);

}
