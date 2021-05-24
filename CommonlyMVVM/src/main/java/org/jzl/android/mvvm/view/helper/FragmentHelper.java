package org.jzl.android.mvvm.view.helper;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IViewBindingHelper;
import org.jzl.android.mvvm.IViewBindingHelperFactory;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.view.core.AbstractMVVMFragment;

public class FragmentHelper<VM extends IViewModel<FragmentHelper<VM, VDB>>, VDB extends ViewDataBinding>
        extends AbstractMVVMFragment<FragmentHelper<VM, VDB>, VM, VDB> {

    private final int layoutResId;
    private final int variableId;
    private final Class<VM> viewModelType;
    private final IViewHelperCallback<FragmentHelper<VM, VDB>, VM, VDB> callback;

    public FragmentHelper(IExtendView<?, ?, ?> parentContainerView, int layoutResId, int variableId, @NonNull Class<VM> viewModelType, IViewHelperCallback<FragmentHelper<VM, VDB>, VM, VDB> callback) {
        super(parentContainerView);
        this.layoutResId = layoutResId;
        this.variableId = variableId;
        this.viewModelType = viewModelType;
        this.callback = callback;
    }

    @NonNull
    @Override
    public IViewBindingHelper<FragmentHelper<VM, VDB>, VM> createViewBindingHelper(IViewBindingHelperFactory factory) {
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
