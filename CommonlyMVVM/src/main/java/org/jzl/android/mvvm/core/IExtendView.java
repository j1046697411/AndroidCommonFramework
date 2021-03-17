package org.jzl.android.mvvm.core;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

public interface IExtendView<V extends IView, VM extends IViewModel, VDB extends ViewDataBinding> extends IView , IViewModelStoreOwner {

    IViewHelper<V, VM> createViewHelper(IViewHelperFactory viewHelperFactory);

    void initialise(@NonNull VDB viewDataBinding, @NonNull VM viewModel);

    VDB getViewDataBinding();

    VM getViewModel();

    IExtendView<?, ?, ?> getParentContainerView();

    @NonNull
    @Override
    IViewModelStore getTargetViewModelStore();
}
