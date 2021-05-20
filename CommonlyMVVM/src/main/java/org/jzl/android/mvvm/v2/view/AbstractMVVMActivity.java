package org.jzl.android.mvvm.v2.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.v2.IExtendView;
import org.jzl.android.mvvm.v2.IView;
import org.jzl.android.mvvm.v2.IViewModel;
import org.jzl.android.mvvm.v2.IViewModelProvider;
import org.jzl.android.mvvm.v2.IViewModelStore;
import org.jzl.android.mvvm.v2.ViewHelper;

public abstract class AbstractMVVMActivity<V extends IView, VM extends IViewModel<V>, VDB extends ViewDataBinding> extends AppCompatActivity implements IExtendView<V, VM, VDB> {

    private final ViewHelper<V, VM, VDB> viewHelper = new ViewHelper<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHelper.setContentView(this, savedInstanceState);
    }

    @NonNull
    @Override
    public final IExtendView<?, ?, ?> getParentContainerView() {
        return this;
    }

    @Override
    public void bindVariable(int variableId, @Nullable Object value) {
        viewHelper.bindVariable(variableId, value);
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
    public IViewModelStore getSelfViewModelStore() {
        return viewHelper.getSelfViewModelStore();
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
    protected void onDestroy() {
        super.onDestroy();
        viewHelper.unbind();
    }
}
