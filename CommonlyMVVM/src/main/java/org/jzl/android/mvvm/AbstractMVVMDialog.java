package org.jzl.android.mvvm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.helper.FragmentViewHelper;
import org.jzl.android.mvvm.router.Router;
import org.jzl.lang.util.ObjectUtils;


public abstract class AbstractMVVMDialog<V extends AbstractMVVMDialog<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding> extends Dialog implements IExtendView<V, VM, VDB> {

    private final FragmentViewHelper<V, VM, VDB> viewHelper = new FragmentViewHelper<>();

    public AbstractMVVMDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(viewHelper.onCreateView(getFragmentView(), getLayoutInflater(), null, savedInstanceState));
    }

    @Override
    public VDB getViewDataBinding() {
        return viewHelper.getViewDataBinding();
    }

    @Override
    public VM getViewModel() {
        return viewHelper.getViewModel();
    }

    @SuppressWarnings("unchecked")
    private V getFragmentView() {
        return (V) this;
    }

    @Override
    public Activity getCurrentActivity() {
        Activity activity = getOwnerActivity();
        if (ObjectUtils.nonNull(activity)) {
            return activity;
        }
        Context context = getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    @Override
    public Router router() {
        return viewHelper.getRouter();
    }

    @Override
    public void bindVariable(int variableId, Object value) {
        viewHelper.bindVariable(variableId, value);
    }
}
