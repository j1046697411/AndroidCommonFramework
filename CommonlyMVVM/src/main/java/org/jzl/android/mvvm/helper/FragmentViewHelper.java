package org.jzl.android.mvvm.helper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IViewModel;


public class FragmentViewHelper<V extends IExtendView<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding> extends ViewHelperWarp<V, VM, VDB> {

    public View onCreateView(V fragment, @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        preBind(fragment);
        VDB viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        bind(fragment, viewDataBinding);
        return viewDataBinding.getRoot();
    }

}
