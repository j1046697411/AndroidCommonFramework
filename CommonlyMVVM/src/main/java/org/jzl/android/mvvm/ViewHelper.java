package org.jzl.android.mvvm;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;

import java.util.Objects;

public class ViewHelper<V extends IView, VM extends IViewModel<V>, VDB extends ViewDataBinding>
        implements IVariableBinder, IViewModelStoreOwner {

    private IViewBindingHelper<V, VM> viewBindingHelper;

    private IExtendView<V, VM, VDB> extendView;
    private VDB dataBinding;
    private VM viewModel;

    public <A extends Activity & IExtendView<V, VM, VDB>> void setContentView(@NonNull A activity, @Nullable Bundle savedInstanceState) {
        perBind(activity);
        bind(activity, viewBindingHelper, DataBindingUtil.setContentView(activity, viewBindingHelper.getLayoutResId()));
    }

    public View inflate(@NonNull IExtendView<V, VM, VDB> extendView, @NonNull LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        perBind(extendView);
        VDB dataBinding = DataBindingUtil.inflate(layoutInflater, viewBindingHelper.getLayoutResId(), parent, false);
        bind(extendView, viewBindingHelper, dataBinding);
        return dataBinding.getRoot();
    }

    public void inflate(@NonNull IExtendView<V, VM, VDB> extendView, @NonNull ViewStubProxy viewStubProxy) {
        perBind(extendView);
        ViewStub viewStub = viewStubProxy.getViewStub();
        Objects.requireNonNull(viewStub, "viewStub is null");
        if (!viewStubProxy.isInflated()) {
            viewStub.setLayoutResource(viewBindingHelper.getLayoutResId());
        }
        VDB dataBinding = DataBindingUtil.bind(viewStub.inflate());
        Objects.requireNonNull(dataBinding, "dataBinding is null");
        bind(extendView, viewBindingHelper, dataBinding);
    }

    public void bind(@NonNull IExtendView<V, VM, VDB> extendView, @NonNull View view) {
        perBind(extendView);
        bind(extendView, viewBindingHelper, Objects.requireNonNull(DataBindingUtil.bind(view), "dataBinding is null"));
    }

    private void bind(@NonNull IExtendView<V, VM, VDB> extendView, @NonNull IViewBindingHelper<V, VM> viewBindingHelper, @NonNull VDB dataBinding) {
        dataBinding.setLifecycleOwner(extendView);
        this.dataBinding = dataBinding;
        this.extendView = extendView;
        if (extendView instanceof IPreBindingView) {
            ((IPreBindingView) extendView).onPreBinding();
        }
        ViewModelProvider<V, VM> viewModelProvider = viewBindingHelper.getViewModelProvider();
        this.viewModel = viewModelProvider.bindViewModel(this, extendView, dataBinding);
    }

    protected void perBind(@NonNull IExtendView<V, VM, VDB> extendView) {
        viewBindingHelper = extendView.createViewBindingHelper(new ViewBindingHelperFactory());
    }

    @Override
    public void bindVariable(int variableId, @Nullable Object value) {
        if (value instanceof IBindVariableOwner) {
            this.dataBinding.setVariable(variableId, ((IBindVariableOwner) value).getBindVariableValue());
        } else {
            this.dataBinding.setVariable(variableId, value);
        }
    }

    @NonNull
    public IViewBindingHelper<V, VM> getViewBindingHelper() {
        return viewBindingHelper;
    }

    @NonNull
    public V getView() {
        return viewBindingHelper.getView();
    }

    @NonNull
    public VDB getViewDataBinding() {
        return dataBinding;
    }

    @NonNull
    public VM getViewModel() {
        return viewModel;
    }

    @NonNull
    public IViewModelFactory getViewModelFactory() {
        return viewBindingHelper.getViewModelFactory();
    }

    @Override
    public IViewModelStore getSelfViewModelStore() {
        return viewBindingHelper.getRealViewModelStore(extendView);
    }

    @NonNull
    public IViewModelProvider getViewModelProvider() {
        return viewBindingHelper.getViewModelProvider();
    }

    public String generateViewModelKey(Class<?> viewModel) {
        return viewModel.getSimpleName();
    }

    public void unbind() {
        viewBindingHelper.unbind();
        dataBinding.unbind();
        extendView.unbind();
    }
}
