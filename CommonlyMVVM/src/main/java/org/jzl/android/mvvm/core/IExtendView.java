package org.jzl.android.mvvm.core;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

public interface IExtendView<V extends IView, VM extends IViewModel, VDB extends ViewDataBinding> extends IView, IViewModelStoreOwner, LifecycleOwner {

    IViewHelper<V, VM> createViewHelper(IViewHelperFactory viewHelperFactory);

    void initialise(@NonNull VDB viewDataBinding, @NonNull VM viewModel);

    VDB getViewDataBinding();

    VM getViewModel();

    IExtendView<?, ?, ?> getParentContainerView();

    @NonNull
    @Override
    IViewModelStore getTargetViewModelStore();
}
