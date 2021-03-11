package org.jzl.android.mvvm.helper;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;


class SimpleViewHelper<V extends IView, VM extends IViewModel, VDB extends ViewDataBinding> implements IViewHelper<V, VM, VDB> {
    @LayoutRes
    private final int layoutId;
    private final int variableId;
    private final VM viewModel;

    public SimpleViewHelper(int layoutId, int variableId, VM viewModel) {
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.viewModel = viewModel;
    }

    @Override
    public void bind(@NonNull V view, @NonNull VDB viewDataBinding) {
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getVariableId() {
        return variableId;
    }

    @Override
    @NonNull
    public VM createViewModel() {
        return viewModel;
    }

    @Override
    public void unbind() {
    }

    public static <V extends IView, VM extends IViewModel, VDB extends ViewDataBinding> IViewHelper<V, VM, VDB> of(int layoutId, int variableId, VM viewModel){
        return new SimpleViewHelper<>(layoutId, variableId, viewModel);
    }
}
