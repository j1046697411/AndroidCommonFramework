package org.jzl.android.mvvm.core;

import android.app.Application;

public interface IView {

    Application getApplication();

    void bindVariable(int variableId, Object value);

    <VM1 extends IViewModel> VM1 bindVariableViewModel(int variableId, Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 bindVariableViewModel(String key, int variableId, Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 getViewModel(String key, Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 getViewModel(Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 createViewModel(Class<VM1> viewModelType);

    <VM1 extends IViewModel> VM1 createViewModel(String key, Class<VM1> viewModelType);

}
