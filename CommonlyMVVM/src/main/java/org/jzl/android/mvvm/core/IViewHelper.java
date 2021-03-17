package org.jzl.android.mvvm.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public interface IViewHelper<V extends IView, VM extends IViewModel> {

    IViewModelFactory getViewModelFactory();

    @NonNull
    V getView();

    @LayoutRes
    int getLayoutId();

    int getVariableId();

    @NonNull
    Class<VM> getViewModelType();

}
