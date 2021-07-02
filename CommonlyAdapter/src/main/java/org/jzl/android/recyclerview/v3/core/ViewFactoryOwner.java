package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

class ViewFactoryOwner implements IViewFactoryOwner {

    @NonNull
    private final IOptions<?, ?> options;
    @NonNull
    private final IViewFactory viewFactory;
    @NonNull
    private final IMatchPolicy matchPolicy;
    private final int priority;

    ViewFactoryOwner(@NonNull IOptions<?, ?> options, @NonNull IViewFactory viewFactory, @NonNull IMatchPolicy matchPolicy, int priority) {
        this.options = options;
        this.viewFactory = viewFactory;
        this.matchPolicy = matchPolicy;
        this.priority = priority;
    }

    @NonNull
    @Override
    public IOptions<?, ?> getOptions() {
        return options;
    }

    @NonNull
    @Override
    public IMatchPolicy getMatchPolicy() {
        return matchPolicy;
    }

    @NonNull
    @Override
    public IViewFactory getViewFactory() {
        return viewFactory;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
