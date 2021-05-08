package org.jzl.android.mvvm.view.helper;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;

import org.jzl.android.mvvm.core.IExtendView;
import org.jzl.android.mvvm.core.IViewHelper;
import org.jzl.android.mvvm.core.IViewHelperFactory;
import org.jzl.android.mvvm.core.IViewModel;
import org.jzl.android.mvvm.view.AbstractMVVMViewStub;
import org.jzl.lang.util.ObjectUtils;

public final class ViewStubHelper<VM extends IViewModel, VDB extends ViewDataBinding> extends AbstractMVVMViewStub<ViewStubHelper<VM, VDB>, VM, VDB> {

    private final Class<VM> viewModelType;
    private final int variableId;
    private final int layoutResId;
    private final IViewHelperCallback<ViewStubHelper<VM, VDB>, VM, VDB> viewHelperCallback;

    ViewStubHelper(IExtendView<?, ?, ?> extendView, ViewStubProxy viewStubProxy, Class<VM> viewModelType, int variableId, int layoutResId, IViewHelperCallback<ViewStubHelper<VM, VDB>, VM, VDB> viewHelperCallback) {
        super(extendView, viewStubProxy);
        this.viewModelType = viewModelType;
        this.variableId = variableId;
        this.layoutResId = layoutResId;
        this.viewHelperCallback = viewHelperCallback;
    }

    public static <VM extends IViewModel, VDB extends ViewDataBinding> ViewStubHelper<VM, VDB> of(IExtendView<?, ?, ?> extendView, ViewStubProxy viewStubProxy, Class<VM> viewModelType, int variableId, int layoutResId, IViewHelperCallback<ViewStubHelper<VM, VDB>, VM, VDB> viewHelperCallback) {
        return new ViewStubHelper<>(extendView, viewStubProxy, viewModelType, variableId, layoutResId, viewHelperCallback);
    }

    public static <VM extends IViewModel, VDB extends ViewDataBinding> ViewStubHelper<VM, VDB> of(IExtendView<?, ?, ?> extendView, ViewStubProxy viewStubProxy, Class<VM> viewModelType, int variableId, int layoutResId) {
        return of(extendView, viewStubProxy, viewModelType, variableId, layoutResId, null);
    }

    @Override
    public IViewHelper<ViewStubHelper<VM, VDB>, VM> createViewHelper(IViewHelperFactory viewHelperFactory) {
        return viewHelperFactory.createViewHelper(this, layoutResId, variableId, viewModelType);
    }

    @Override
    public void initialise(@NonNull VDB viewDataBinding, @NonNull VM viewModel) {
        if (ObjectUtils.nonNull(viewHelperCallback)) {
            viewHelperCallback.initialise(this, viewModel, viewDataBinding);
        }
    }

}
