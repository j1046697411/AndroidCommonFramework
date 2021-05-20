package org.jzl.android.mvvm;

public interface IViewModelFactory {

    <V extends IView, VM extends IViewModel<V>> VM createViewModel(ViewHelper<?, ?, ?> viewHelper, IExtendView<?, ?, ?> extendView,String viewModelKey, Class<VM> viewModelType);

}
