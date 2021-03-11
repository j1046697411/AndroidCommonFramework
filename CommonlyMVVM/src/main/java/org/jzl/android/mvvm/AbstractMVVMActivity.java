package org.jzl.android.mvvm;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.helper.ActivityViewHelper;
import org.jzl.android.mvvm.router.Router;


public abstract class AbstractMVVMActivity<V extends AbstractMVVMActivity<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding> extends
        AppCompatActivity implements IExtendView<V, VM, VDB> {

    private final ActivityViewHelper<V, VM, VDB> viewHelper = new ActivityViewHelper<>();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHelper.onCreate(getView(), savedInstanceState);
    }

    @Override
    public abstract void initialise(@NonNull VDB viewDataBinding, @NonNull VM viewModel);

    @Override
    public final void bindVariable(int variableId, Object value) {
        viewHelper.bindVariable(variableId, value);
    }

    @SuppressWarnings("unchecked")
    private V getView() {
        return (V) this;
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public VDB getViewDataBinding() {
        return viewHelper.getViewDataBinding();
    }

    @Override
    public VM getViewModel() {
        return viewHelper.getViewModel();
    }

    @Override
    public Router router() {
        return viewHelper.getRouter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewHelper.unbind();
    }
}
