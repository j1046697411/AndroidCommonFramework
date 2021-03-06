package org.jzl.android.mvvm;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import org.jzl.android.mvvm.helper.FragmentViewHelper;
import org.jzl.android.mvvm.router.Router;

public abstract class AbstractMVVMFragment<V extends AbstractMVVMFragment<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding>
        extends Fragment implements IExtendView<V, VM, VDB> {

    private FragmentViewHelper<V, VM, VDB> viewHelper = new FragmentViewHelper<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return viewHelper.onCreateView(getFragmentView(), inflater, container, savedInstanceState);
    }

    @Override
    public abstract void initialise(@NonNull VDB viewDataBinding, @NonNull VM viewModel);

    @Override
    public void bindVariable(int variableId, Object value) {
        viewHelper.bindVariable(variableId, value);
    }

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
        viewHelper = null;
    }
}
