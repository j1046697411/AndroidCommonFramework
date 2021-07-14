package org.jzl.android.recyclerview.v3.core.diff;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IPlugin;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

import java.util.List;

public interface IDiffDispatcher<T, VH extends IViewHolder> extends IPlugin<T, VH> {

    void submitList(List<T> newList);

    static <T, VH extends IViewHolder> IDiffDispatcher<T, VH> of(@NonNull IItemCallback<T> itemCallback){
        return new AsyncDiffDispatcher<>(itemCallback);
    }

}
