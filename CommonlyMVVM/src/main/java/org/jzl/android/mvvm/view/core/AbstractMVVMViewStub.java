package org.jzl.android.mvvm.view.core;

import android.app.Application;
import android.view.View;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;
import androidx.lifecycle.Lifecycle;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.IViewModelProvider;
import org.jzl.android.mvvm.IViewModelStore;
import org.jzl.android.mvvm.ViewHelper;
import org.jzl.android.mvvm.view.IViewStubView;

public abstract class AbstractMVVMViewStub<V extends IView, VM extends IViewModel<V>, VDB extends ViewDataBinding>
        implements IExtendView<V, VM, VDB>, IViewStubView {

    private final ViewStubProxy viewStubProxy;
    private final ViewHelper<V, VM, VDB> viewHelper = new ViewHelper<>();
    private final IExtendView<?, ?, ?> parentContainerView;

    public AbstractMVVMViewStub(IExtendView<?, ?, ?> parentContainerView, ViewStub viewStub) {
        this(parentContainerView, new ViewStubProxy(viewStub));
    }

    public AbstractMVVMViewStub(IExtendView<?, ?, ?> parentContainerView, ViewStubProxy viewStubProxy) {
        this.viewStubProxy = viewStubProxy;
        viewStubProxy.setContainingBinding(parentContainerView.getViewDataBinding());
        this.parentContainerView = parentContainerView;
    }

    @Override
    public void inflate() {
        viewHelper.inflate(this, viewStubProxy);
    }

    @NonNull
    @Override
    public IExtendView<?, ?, ?> getParentContainerView() {
        return parentContainerView;
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
        return parentContainerView.getApplication();
    }

    @NonNull
    @Override
    public IViewModelProvider getViewModelProvider() {
        return viewHelper.getViewModelProvider();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return parentContainerView.getLifecycle();
    }

    @Override
    public void bindVariable(int variableId, @Nullable Object value) {
        viewHelper.bindVariable(variableId, value);
    }

    @Override
    public void unbind() {
    }

    @Override
    public IViewModelStore getSelfViewModelStore() {
        return viewHelper.getSelfViewModelStore();
    }
}
