package org.jzl.android.recyclerview.v3.core.vh;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;

public interface IObserver<VH extends IViewHolder> {

    void onClickItemView(@NonNull IViewHolderOwner<VH> owner);

    boolean onLongClickItemView(@NonNull IViewHolderOwner<VH> owner);
}
