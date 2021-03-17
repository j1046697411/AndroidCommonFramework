package org.jzl.android.mvvm.core;

import androidx.lifecycle.LifecycleOwner;

public interface IView extends LifecycleOwner {

    void bindVariable(int variableId, Object value);

    <VM extends IViewModel> VM bindVariableViewModel(int variableId, Class<VM> viewModelType);

    <VM extends IViewModel> VM bindVariableViewModel(String key, int variableId, Class<VM> viewModelType);

    <VM extends IViewModel> VM getViewModel(String key, Class<VM> viewModelType);

    <VM extends IViewModel> VM getViewModel(Class<VM> viewModelType);

    <VM1 extends IViewModel> VM1 createViewModel(Class<VM1> viewModelType) ;

    <VM1 extends IViewModel> VM1 createViewModel(String key, Class<VM1> viewModelType);

}
