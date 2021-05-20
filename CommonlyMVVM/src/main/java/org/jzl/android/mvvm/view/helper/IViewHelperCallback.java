package org.jzl.android.mvvm.view.helper;

import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IViewModel;

@FunctionalInterface
public interface IViewHelperCallback<V extends IExtendView<V, VM, VDB>, VM extends IViewModel<V>, VDB extends ViewDataBinding> {

    void initialise(V view, VDB viewDataBinding, VM viewModel);

    default void unbind(){
    }
}
