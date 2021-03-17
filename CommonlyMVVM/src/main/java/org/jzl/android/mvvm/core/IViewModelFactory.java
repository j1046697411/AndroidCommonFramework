package org.jzl.android.mvvm.core;

public interface IViewModelFactory {

    <VM extends IViewModel> VM create(Class<VM> viewModelType);

}
