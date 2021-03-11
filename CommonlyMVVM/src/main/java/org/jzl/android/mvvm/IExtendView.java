package org.jzl.android.mvvm;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.helper.IViewHelper;
import org.jzl.android.mvvm.helper.IViewHelperFactory;


public interface IExtendView<V extends IView,VM extends IViewModel, VDB extends ViewDataBinding> extends IView {

    Activity getCurrentActivity();

    @Override
    default void showToast(String text){
        Toast.makeText(getCurrentActivity(), text, Toast.LENGTH_LONG).show();
    }

    IViewHelper<V,VM, VDB> createViewHelper(IViewHelperFactory viewHelperFactory);

    void initialise(@NonNull VDB viewDataBinding, @NonNull VM viewModel);

    VDB getViewDataBinding();

    VM getViewModel();

}
