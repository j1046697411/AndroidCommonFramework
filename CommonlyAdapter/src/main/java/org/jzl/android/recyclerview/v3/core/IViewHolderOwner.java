package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IViewHolderOwner<T, VH extends IViewHolder> {

    @NonNull
    IViewHolderFactory<T, VH> getViewHolderFactory();

}
