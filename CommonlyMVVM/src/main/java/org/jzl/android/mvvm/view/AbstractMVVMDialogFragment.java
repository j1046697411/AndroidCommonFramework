package org.jzl.android.mvvm.view;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.core.IExtendView;
import org.jzl.android.mvvm.core.IPreBindingView;
import org.jzl.android.mvvm.core.IViewModel;
import org.jzl.android.mvvm.core.IViewModelStore;
import org.jzl.android.mvvm.core.ViewStore;
import org.jzl.android.mvvm.vm.DialogViewModel;
import org.jzl.lang.util.ObjectUtils;

public abstract class AbstractMVVMDialogFragment<V extends AbstractMVVMDialogFragment<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding>
        extends AppCompatDialogFragment implements IExtendView<V, VM, VDB>, IPreBindingView {

    private final ViewStore<V, VM, VDB> viewStore = new ViewStore<>();
    private DialogViewModel dialogViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return viewStore.inflate(this, inflater, container, savedInstanceState);
    }

    @Override
    public void onPreBinding() {
        dialogViewModel = createViewModel(DialogViewModel.class);
        bindDialogViewModel(dialogViewModel);
    }

    @Override
    public void bindVariable(int variableId, Object value) {
        viewStore.bindVariable(variableId, value);
    }

    protected void bindDialogViewModel(DialogViewModel dialogViewModel){
        dialogViewModel.dismiss.observe(this, aBoolean -> {
            if (ObjectUtils.nonNull(aBoolean) && aBoolean){
                dismiss();
            }
        });
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
    public void onDestroy() {
        super.onDestroy();
        viewStore.onDestroy();
    }

    @NonNull
    @Override
    public IViewModelStore getTargetViewModelStore() {
        return viewStore.getTargetViewModelStore();
    }

    @Override
    public IExtendView<?, ?, ?> getParentContainerView() {
        Activity activity = getActivity();
        if (activity instanceof IExtendView<?, ?, ?>) {
            return (IExtendView<?, ?, ?>) activity;
        } else {
            return this;
        }
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
    public <VM1 extends IViewModel> VM1 createViewModel(String key, Class<VM1> viewModelType) {
        return viewStore.createViewModel(key, viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 createViewModel(Class<VM1> viewModelType) {
        return viewStore.createViewModel(viewModelType);
    }
}
