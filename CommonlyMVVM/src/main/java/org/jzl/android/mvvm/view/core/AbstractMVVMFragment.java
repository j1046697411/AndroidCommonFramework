package org.jzl.android.mvvm.view.core;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.IViewModelProvider;
import org.jzl.android.mvvm.IViewModelStore;
import org.jzl.android.mvvm.ViewHelper;
import org.jzl.android.mvvm.view.IFragmentView;
import org.jzl.lang.util.ObjectUtils;

public abstract class AbstractMVVMFragment<V extends IView, VM extends IViewModel<V>, VDB extends ViewDataBinding> extends Fragment
        implements IExtendView<V, VM, VDB>, IFragmentView {


    private final IExtendView<?, ?, ?> parentContainerView;
    private final ViewHelper<V, VM, VDB> viewHelper = new ViewHelper<>();

    public AbstractMVVMFragment() {
        this(null);
    }

    public AbstractMVVMFragment(IExtendView<?, ?, ?> parentContainerView) {
        this.parentContainerView = parentContainerView;
    }

    @NonNull
    @Override
    public IExtendView<?, ?, ?> getParentContainerView() {
        if (ObjectUtils.nonNull(parentContainerView)) {
            return parentContainerView;
        }
        Activity activity = requireActivity();
        if (activity instanceof IExtendView) {
            return (IExtendView<?, ?, ?>) activity;
        }
        return this;
    }

    @NonNull
    @Override
    public VM getViewModel() {
        return viewHelper.getViewModel();
    }

    @NonNull
    @Override
    public VDB getViewDataBinding() {
        return viewHelper.getViewDataBinding();
    }

    @NonNull
    @Override
    public V getSelfView() {
        return viewHelper.getView();
    }

    @Override
    public Application getApplication() {
        return requireActivity().getApplication();
    }

    @Override
    public void bindVariable(int variableId, @Nullable Object value) {
        viewHelper.bindVariable(variableId, value);
    }

    @NonNull
    @Override
    public IViewModelProvider getViewModelProvider() {
        return viewHelper.getViewModelProvider();
    }

    @Override
    public void unbind() {
    }

    @Override
    public IViewModelStore getSelfViewModelStore() {
        return viewHelper.getSelfViewModelStore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewHelper.unbind();
    }
}
