package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import java.util.List;

public interface IViewFactoryStore<VH extends IViewHolder> extends IViewFactoryOwner<VH> {

    @NonNull
    @Override
    IOptions<?, VH> getOptions();

    @NonNull
    @Override
    IViewFactory getViewFactory();

    @NonNull
    @Override
    IMatchPolicy getMatchPolicy();

    @Override
    int getPriority();

    IViewFactoryOwner<VH> get(int itemViewType);

    @NonNull
    List<IViewFactoryOwner<VH>> getUnmodifiableViewFactoryOwners();

}
