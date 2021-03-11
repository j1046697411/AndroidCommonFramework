package org.jzl.android.mvvm.helper;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;


public interface IViewHelper<V extends IView, VM extends IViewModel, VDB extends ViewDataBinding> {

    void bind(@NonNull V view, @NonNull VDB viewDataBinding);

    @LayoutRes
    int getLayoutId();

    int getVariableId();

    @NonNull
    VM createViewModel();

    void unbind();

}
