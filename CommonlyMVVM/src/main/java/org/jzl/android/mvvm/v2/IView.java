package org.jzl.android.mvvm.v2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public interface IView extends LifecycleOwner, IVariableBinder {

    Application getApplication();

    @NonNull
    @Override
    Lifecycle getLifecycle();

    @NonNull
    IViewModelProvider getViewModelProvider();

    @Override
    void bindVariable(int variableId, @Nullable Object value);

    void unbind();

}
