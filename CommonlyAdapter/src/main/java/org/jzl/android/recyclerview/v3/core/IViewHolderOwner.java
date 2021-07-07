package org.jzl.android.recyclerview.v3.core;

import android.view.View;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.vh.IObservable;

public interface IViewHolderOwner<VH extends IViewHolder> {

    @NonNull
    IContext getContext();

    @NonNull
    View getItemView();

    @NonNull
    VH getViewHolder();

    @NonNull
    IObservable<VH> getObservable();
}
