package org.jzl.android.mvvm.helper;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IViewModel;


public class ActivityViewHelper<V extends Activity & IExtendView<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding> extends ViewHelperWarp<V, VM, VDB> implements IViewHelper<V, VM, VDB> {


    public void onCreate(@NonNull V activity, @Nullable Bundle savedInstanceState) {
        preBind(activity);
        bind(activity, DataBindingUtil.setContentView(activity, getLayoutId()));
    }
}
