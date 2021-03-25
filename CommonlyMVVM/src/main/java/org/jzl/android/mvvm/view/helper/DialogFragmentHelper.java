package org.jzl.android.mvvm.view.helper;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.core.IViewHelper;
import org.jzl.android.mvvm.core.IViewHelperFactory;
import org.jzl.android.mvvm.core.IViewModel;
import org.jzl.android.mvvm.view.AbstractMVVMDialogFragment;
import org.jzl.lang.util.ObjectUtils;

public final class DialogFragmentHelper<VM extends IViewModel, VDB extends ViewDataBinding> extends AbstractMVVMDialogFragment<DialogFragmentHelper<VM, VDB>, VM, VDB> {

    private final Class<VM> viewModelType;
    private final int variableId;
    private final int layoutResId;
    private final IViewHelperCallback<DialogFragmentHelper<VM, VDB>, VM, VDB> viewHelperCallback;

    DialogFragmentHelper(Class<VM> viewModelType, int variableId, int layoutResId, IViewHelperCallback<DialogFragmentHelper<VM, VDB>, VM, VDB> viewHelperCallback) {
        this.viewModelType = viewModelType;
        this.variableId = variableId;
        this.layoutResId = layoutResId;
        this.viewHelperCallback = viewHelperCallback;
    }

    public static <VM extends IViewModel, VDB extends ViewDataBinding> DialogFragmentHelper<VM, VDB> of(Class<VM> viewModelType, int variableId, int layoutResId, IViewHelperCallback<DialogFragmentHelper<VM, VDB>, VM, VDB> viewHelperCallback) {
        return new DialogFragmentHelper<>(viewModelType, variableId, layoutResId, viewHelperCallback);
    }

    public static <VM extends IViewModel, VDB extends ViewDataBinding> DialogFragmentHelper<VM, VDB> of(Class<VM> viewModelType, int variableId, int layoutResId) {
        return of(viewModelType, variableId, layoutResId, null);
    }

    @Override
    public IViewHelper<DialogFragmentHelper<VM, VDB>, VM> createViewHelper(IViewHelperFactory viewHelperFactory) {
        return viewHelperFactory.createViewHelper(this, layoutResId, variableId, viewModelType);
    }

    @Override
    public void initialise(@NonNull VDB viewDataBinding, @NonNull VM viewModel) {
        if (ObjectUtils.nonNull(viewHelperCallback)) {
            viewHelperCallback.initialise(this, viewModel, viewDataBinding);
        }
    }
}
