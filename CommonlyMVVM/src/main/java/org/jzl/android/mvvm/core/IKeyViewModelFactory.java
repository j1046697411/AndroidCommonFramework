package org.jzl.android.mvvm.core;

public interface IKeyViewModelFactory extends IViewModelFactory {

    <VM extends IViewModel> VM create(String key, Class<VM> viewModelType);

}
