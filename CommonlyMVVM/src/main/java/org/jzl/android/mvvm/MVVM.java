package org.jzl.android.mvvm;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;

import org.jzl.android.mvvm.view.helper.DialogFragmentHelper;
import org.jzl.android.mvvm.view.helper.FragmentHelper;
import org.jzl.android.mvvm.view.helper.IViewHelperCallback;
import org.jzl.android.mvvm.view.helper.ViewStubHelper;
import org.jzl.android.mvvm.vm.HelperViewModel;

@SuppressWarnings("all")
public final class MVVM {

    public static <VM extends IViewModel<DialogFragmentHelper<VM, VDB>>, VDB extends ViewDataBinding> DialogFragmentHelper<VM, VDB> dialog(IExtendView<?, ?, ?> parentContainerView, int layoutResId, int variableId, @NonNull Class<VM> viewModelType, IViewHelperCallback<DialogFragmentHelper<VM, VDB>, VM, VDB> callback) {
        return new DialogFragmentHelper<>(parentContainerView, layoutResId, variableId, viewModelType, callback);
    }

    public static <VM extends IViewModel<DialogFragmentHelper<VM, VDB>>, VDB extends ViewDataBinding> DialogFragmentHelper<VM, VDB> dialog(int layoutResId, int variableId, @NonNull Class<VM> viewModelType, IViewHelperCallback<DialogFragmentHelper<VM, VDB>, VM, VDB> callback) {
        return dialog(null, layoutResId, variableId, viewModelType, callback);
    }

    public static <VM extends IViewModel<FragmentHelper<VM, VDB>>, VDB extends ViewDataBinding> FragmentHelper<VM, VDB> fragment(IExtendView<?, ?, ?> parentContainerView, int layoutResId, int variableId, @NonNull Class<VM> viewModelType, IViewHelperCallback<FragmentHelper<VM, VDB>, VM, VDB> callback) {
        return new FragmentHelper<>(parentContainerView, layoutResId, variableId, viewModelType, callback);
    }

    public static <VM extends IViewModel<FragmentHelper<VM, VDB>>, VDB extends ViewDataBinding> FragmentHelper<VM, VDB> fragment(int layoutResId, int variableId, @NonNull Class<VM> viewModelType, IViewHelperCallback<FragmentHelper<VM, VDB>, VM, VDB> callback) {
        return fragment(null, layoutResId, variableId, viewModelType, callback);
    }

    public static <VM extends IViewModel<ViewStubHelper<VM, VDB>>, VDB extends ViewDataBinding> ViewStubHelper<VM, VDB> viewStub(IExtendView<?, ?, ?> parentContainerView, ViewStubProxy viewStubProxy, int layoutResId, int variableId, @NonNull Class<VM> viewModelType, IViewHelperCallback<ViewStubHelper<VM, VDB>, VM, VDB> callback) {
        return new ViewStubHelper<>(parentContainerView, viewStubProxy, layoutResId, variableId, viewModelType, callback);
    }

}
