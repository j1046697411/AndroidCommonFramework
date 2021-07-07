package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IViewHolderFactoryOwner<VH extends IViewHolder> {

    @NonNull
    IViewHolderFactory<VH> getViewHolderFactory();

}
