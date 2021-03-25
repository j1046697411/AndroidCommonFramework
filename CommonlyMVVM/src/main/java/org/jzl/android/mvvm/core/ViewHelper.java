package org.jzl.android.mvvm.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

class ViewHelper<V extends IView, VM extends IViewModel> implements IViewHelper<V, VM> {

    private final V view;
    @LayoutRes
    private final int layoutId;
    private final int variableId;
    private final Class<VM> viewModelType;
    private final IViewModelFactory viewModelFactory;

    ViewHelper(V view, int layoutId, int variableId, Class<VM> viewModelType, IViewModelFactory viewModelFactory) {
        this.view = view;
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.viewModelType = viewModelType;
        this.viewModelFactory = viewModelFactory;
    }

    static <V extends IView, VM extends IViewModel> IViewHelper<V, VM> of(V view, int layoutId, int variableId, Class<VM> viewModelType, IViewModelFactory viewModelFactory) {
        return new ViewHelper<>(view, layoutId, variableId, viewModelType, viewModelFactory);
    }

    static <V extends IView, VM extends IViewModel> IViewHelper<V, VM> of(V view, int layoutId, int variableId, Class<VM> viewModelType) {
        return of(view, layoutId, variableId, viewModelType, ViewModelProviders.DEFAULT_VIEW_MODEL_FACTORY);
    }

    @Override
    public IViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    @Override
    @NonNull
    public V getView() {
        return view;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getVariableId() {
        return variableId;
    }

    @NonNull
    @Override
    public Class<VM> getViewModelType() {
        return viewModelType;
    }

}
