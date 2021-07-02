package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IViewFactoryOwner extends Comparable<IViewFactoryOwner> {

    int DEFAULT_PRIORITY = 10;

    @NonNull
    IOptions<?, ?> getOptions();

    @NonNull
    IMatchPolicy getMatchPolicy();

    @NonNull
    IViewFactory getViewFactory();

    int getPriority();

    @Override
    default int compareTo(IViewFactoryOwner o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
