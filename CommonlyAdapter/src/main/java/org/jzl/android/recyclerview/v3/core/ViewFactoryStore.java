package org.jzl.android.recyclerview.v3.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

class ViewFactoryStore implements IViewFactoryStore, IViewFactory, IMatchPolicy {

    private final IOptions<?, ?> options;
    private final List<IViewFactoryOwner> viewFactoryOwners;
    private final List<IViewFactoryOwner> unmodifiableViewFactoryOwners;

    ViewFactoryStore(IOptions<?, ?> options, List<IViewFactoryOwner> viewFactoryOwners) {
        this.viewFactoryOwners = viewFactoryOwners;
        Collections.sort(this.viewFactoryOwners, Collections.reverseOrder());
        this.options = options;
        this.unmodifiableViewFactoryOwners = Collections.unmodifiableList(viewFactoryOwners);
    }

    @NonNull
    @Override
    public IOptions<?, ?> getOptions() {
        return options;
    }

    @NonNull
    @Override
    public IViewFactory getViewFactory() {
        return this;
    }

    @NonNull
    @Override
    public IMatchPolicy getMatchPolicy() {
        return this;
    }

    @Override
    public int getPriority() {
        return options.getPriority();
    }

    @Override
    public IViewFactoryOwner get(int itemViewType) {
        return ForeachUtils.findByOne(this.viewFactoryOwners, target -> target.getMatchPolicy().match(itemViewType));
    }

    @NonNull
    @Override
    public List<IViewFactoryOwner> getUnmodifiableViewFactoryOwners() {
        return unmodifiableViewFactoryOwners;
    }

    @Override
    public View create(LayoutInflater layoutInflater, ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public boolean match(int itemViewType) {
        return ObjectUtils.nonNull(get(itemViewType));
    }
}
