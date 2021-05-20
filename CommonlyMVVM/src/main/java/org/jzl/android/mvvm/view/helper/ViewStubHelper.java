package org.jzl.android.mvvm.view.helper;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IViewBindingHelper;
import org.jzl.android.mvvm.IViewBindingHelperFactory;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.view.core.AbstractMVVMViewStub;

public class ViewStubHelper<VM extends IViewModel<ViewStubHelper<VM, VDB>>, VDB extends ViewDataBinding> extends AbstractMVVMViewStub<ViewStubHelper<VM, VDB>, VM, VDB> {

    private final int layoutResId;
    private final int variableId;
    private final Class<VM> viewModelType;
    private final IViewHelperCallback<ViewStubHelper<VM, VDB>, VM, VDB> callback;

    public ViewStubHelper(IExtendView<?, ?, ?> parentContainerView, ViewStubProxy viewStubProxy, int layoutResId, int variableId, @NonNull Class<VM> viewModelType, IViewHelperCallback<ViewStubHelper<VM, VDB>, VM, VDB> callback) {
        super(parentContainerView, viewStubProxy);
        this.layoutResId = layoutResId;
        this.variableId = variableId;
        this.viewModelType = viewModelType;
        this.callback = callback;
    }

    @NonNull
    @Override
    public IViewBindingHelper<ViewStubHelper<VM, VDB>, VM> createViewBindingHelper(IViewBindingHelperFactory factory) {
        return factory.createViewBindingHelper(this, layoutResId, variableId, viewModelType);
    }

    @Override
    public void initialize(@NonNull VDB dataBinding, VM viewModel) {
        callback.initialise(this, dataBinding, viewModel);
    }

    @Override
    public void unbind() {
        super.unbind();
        callback.unbind();
    }
}
