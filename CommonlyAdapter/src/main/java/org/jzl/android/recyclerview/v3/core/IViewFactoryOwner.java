package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IViewFactoryOwner<VH extends IViewHolder> extends Comparable<IViewFactoryOwner<?>> {

    int DEFAULT_PRIORITY = 10;

    @NonNull
    IOptions<?, VH> getOptions();

    @NonNull
    IMatchPolicy getMatchPolicy();

    @NonNull
    IViewFactory getViewFactory();

    int getPriority();

    @Override
    default int compareTo(IViewFactoryOwner<?> o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
