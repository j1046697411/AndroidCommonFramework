package org.jzl.android.mvvm;

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

    IViewModelStore getRealViewModelStore(@NonNull IExtendView<V, VM, ?> extendView);

    void unbind();
}
