package org.jzl.android.mvvm.v2;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;


public interface IExtendView<V extends IView, VM extends IViewModel<V>, VDB extends ViewDataBinding> extends IView , IViewModelStoreOwner{

    @NonNull
    IExtendView<?, ?, ?> getParentContainerView();

    @NonNull
    IViewBindingHelper<V, VM> createViewBindingHelper(IViewBindingHelperFactory factory);

    void initialize(@NonNull VDB dataBinding, VM viewModel);

    @NonNull
    VM getViewModel();

    @NonNull
    VDB getViewDataBinding();

    @NonNull
    V getSelfView();

}
