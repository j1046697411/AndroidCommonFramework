package org.jzl.android.mvvm.helper;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;


public interface IViewHelperFactory {

    @NonNull
    <V extends IView, VM extends IViewModel, VDB extends ViewDataBinding> IViewHelper<V, VM, VDB> createViewHelper(V view, @LayoutRes int layoutId, int variableId, VM viewModel);

    @NonNull
    <V extends IView, VM extends IViewModel, VDB extends ViewDataBinding> IViewHelper<V, VM, VDB> createViewHelper(V view, @LayoutRes int layoutId, int variableId, Class<VM> viewModelType);
}
