package org.jzl.android.mvvm.view;

import android.view.View;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

import org.jzl.android.mvvm.core.IExtendView;
import org.jzl.android.mvvm.core.IViewModel;
import org.jzl.android.mvvm.core.IViewModelStore;
import org.jzl.android.mvvm.core.ViewStore;

public abstract class AbstractMVVMViewStub<V extends AbstractMVVMViewStub<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding>
        implements IExtendView<V, VM, VDB> {

    private final ViewStore<V, VM, VDB> viewStore = new ViewStore<>();
    private final IExtendView<?, ?, ?> extendView;
    private final ViewStub viewStub;

    public AbstractMVVMViewStub(IExtendView<?, ?, ?> extendView, ViewStub viewStub) {
        this.extendView = extendView;
        this.viewStub = viewStub;
        viewStub.setOnInflateListener((stub, inflated) -> viewStore.bind(this, inflated));
        extendView.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                onDestroy();
            }
        });
    }

    public View inflate() {
        if (viewStub.getLayoutResource() == 0) {
            viewStub.setLayoutResource(viewStore.getViewHelper(this).getLayoutId());
        }
        return viewStub.inflate();
    }

    @Override
    public VDB getViewDataBinding() {
        return viewStore.getViewDataBinding();
    }

    @Override
    public VM getViewModel() {
        return viewStore.getViewModel();
    }

    @Override
    public IExtendView<?, ?, ?> getParentContainerView() {
        return extendView;
    }

    @NonNull
    @Override
    public IViewModelStore getTargetViewModelStore() {
        return viewStore.getTargetViewModelStore();
    }

    @Override
    public void bindVariable(int variableId, Object value) {
        viewStore.bindVariable(variableId, value);
    }

    @Override
    public <VM1 extends IViewModel> VM1 bindVariableViewModel(int variableId, Class<VM1> viewModelType) {
        return viewStore.bindVariableViewModel(variableId, viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 bindVariableViewModel(String key, int variableId, Class<VM1> viewModelType) {
        return viewStore.bindVariableViewModel(key, variableId, viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 getViewModel(String key, Class<VM1> viewModelType) {
        return viewStore.getViewModel(key, viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 getViewModel(Class<VM1> viewModelType) {
        return viewStore.getViewModel(viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 createViewModel(Class<VM1> viewModelType) {
        return viewStore.createViewModel(viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 createViewModel(String key, Class<VM1> viewModelType) {
        return viewStore.createViewModel(key, viewModelType);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return extendView.getLifecycle();
    }

    protected void onDestroy() {
        viewStore.onDestroy();
    }
}
