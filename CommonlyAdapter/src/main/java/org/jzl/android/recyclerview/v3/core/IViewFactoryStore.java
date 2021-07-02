package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import java.util.List;

public interface IViewFactoryStore extends IViewFactoryOwner {

    @NonNull
    @Override
    IOptions<?, ?> getOptions();

    @NonNull
    @Override
    IViewFactory getViewFactory();

    @NonNull
    @Override
    IMatchPolicy getMatchPolicy();

    @Override
    int getPriority();

    IViewFactoryOwner get(int itemViewType);

    @NonNull
    List<IViewFactoryOwner> getUnmodifiableViewFactoryOwners();

}
