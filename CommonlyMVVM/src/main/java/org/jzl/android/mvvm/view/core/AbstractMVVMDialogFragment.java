package org.jzl.android.mvvm.view.core;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.IViewModelProvider;
import org.jzl.android.mvvm.IViewModelStore;
import org.jzl.android.mvvm.ViewHelper;
import org.jzl.android.mvvm.view.IActivityView;
import org.jzl.android.mvvm.view.IDialogFragment;
import org.jzl.lang.util.ObjectUtils;

public abstract class AbstractMVVMDialogFragment<V extends IView, VM extends IViewModel<V>, VDB extends ViewDataBinding> extends DialogFragment
        implements IExtendView<V, VM, VDB>, IDialogFragment {

    private final ViewHelper<V, VM, VDB> viewHelper = new ViewHelper<>();
    private final IExtendView<?, ?, ?> parentContainerView;

    public AbstractMVVMDialogFragment() {
        this(null);
    }

    public AbstractMVVMDialogFragment(IExtendView<?, ?, ?> parentContainerView) {
        this.parentContainerView = parentContainerView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return viewHelper.inflate(this, inflater, container);
    }

    @NonNull
    @Override
    public IExtendView<?, ?, ?> getParentContainerView() {
        if (parentContainerView != null) {
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

    @NonNull
    @Override
    public IViewModelProvider getViewModelProvider() {
        return viewHelper.getViewModelProvider();
    }

    @Override
    public Application getApplication() {
        return requireActivity().getApplication();
    }

    @Override
    public void bindVariable(int variableId, @Nullable Object value) {
        viewHelper.bindVariable(variableId, value);
    }

    @Override
    public void show(@NonNull IActivityView activityView, String tag) {
        this.show(activityView.getSupportFragmentManager(), tag);
    }

    @Override
    public void showNow(@NonNull IActivityView activityView, String tag) {
        this.showNow(activityView.getSupportFragmentManager(), tag);
    }

    @Override
    public boolean isShowing() {
        return isVisible();
    }

    @Override
    public void cancel() {
        Dialog dialog = getDialog();
        if (ObjectUtils.nonNull(dialog)) {
            dialog.cancel();
        }
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
