package org.jzl.android.mvvm.core;

import android.app.Application;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public interface IViewHelperFactory {

    @NonNull
    <V extends IView, VM extends IViewModel> IViewHelper<V, VM> createViewHelper(V view, @LayoutRes int layoutId, int variableId, Class<VM> viewModelType);

    <V extends IView, VM extends IViewModel> IViewHelper<V, VM> createViewHelper(V view, @LayoutRes int layoutId, int variableId, Class<VM> viewModelType, IViewModelFactory viewModelFactory);

    <V extends IView, VM extends IViewModel> IViewHelper<V, VM> createViewHelper(Application application, V view, @LayoutRes int layoutId, int variableId, Class<VM> viewModelType);
}