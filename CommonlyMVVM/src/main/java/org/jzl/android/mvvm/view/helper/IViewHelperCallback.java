package org.jzl.android.mvvm.view.helper;

import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.core.IView;
import org.jzl.android.mvvm.core.IViewModel;

public interface IViewHelperCallback<V extends IView, VM extends IViewModel, VDB extends ViewDataBinding> {

    void initialise(V view, VM viewModel, VDB viewDataBinding);

}
