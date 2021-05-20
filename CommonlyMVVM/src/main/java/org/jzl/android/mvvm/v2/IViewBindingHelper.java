package org.jzl.android.mvvm.v2;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public interface IViewBindingHelper<V extends IView, VM extends IViewModel<V>> extends IViewModelStoreOwner{

    String getViewModelKey();

    @LayoutRes
    int getLayoutResId();

    int getVariableId();

    @NonNull
    V getView();

    @NonNull
    Class<VM> getViewModelType();

    @NonNull
    IViewModelFactory getViewModelFactory();

    @NonNull
    ViewModelProvider<V, VM> getViewModelProvider();

    @Override
    IViewModelStore getSelfViewModelStore();

    void unbind();
}
