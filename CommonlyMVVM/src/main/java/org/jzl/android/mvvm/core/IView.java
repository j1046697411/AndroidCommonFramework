package org.jzl.android.mvvm.core;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public interface IView extends LifecycleOwner {

    Application getApplication();

    @NonNull
    @Override
    Lifecycle getLifecycle();

    void bindVariable(int variableId, Object value);

    <VM1 extends IViewModel> VM1 bindVariableViewModel(int variableId, Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 bindVariableViewModel(String key, int variableId, Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 getViewModel(String key, Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 getViewModel(Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 createViewModel(Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 createViewModel(String key, Class<VM1> viewModelType);

}
