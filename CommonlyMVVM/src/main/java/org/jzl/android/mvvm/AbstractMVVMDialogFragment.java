package org.jzl.android.mvvm;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import org.jzl.android.mvvm.helper.FragmentViewHelper;
import org.jzl.android.mvvm.router.Router;


public abstract class AbstractMVVMDialogFragment<V extends AbstractMVVMDialogFragment<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding> extends DialogFragment implements IExtendView<V, VM, VDB>{

    private final FragmentViewHelper<V, VM, VDB> viewHelper = new FragmentViewHelper<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return viewHelper.onCreateView(getFragmentView(), inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public abstract void initialise(@NonNull VDB viewDataBinding, @NonNull VM viewModel);

    @SuppressWarnings("unchecked")
    private V getFragmentView() {
        return (V) this;
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
    public void bindVariable(int variableId, Object value) {
        viewHelper.bindVariable(variableId, value);
    }

    @Override
    public Activity getCurrentActivity() {
        return getActivity();
    }

    @Override
    public Router router() {
        return viewHelper.getRouter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewHelper.unbind();
    }
}
