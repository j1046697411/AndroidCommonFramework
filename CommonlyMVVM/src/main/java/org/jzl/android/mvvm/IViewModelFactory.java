package org.jzl.android.mvvm;

/**
 * ViewModel 创建的工厂接口
 */

public interface IViewModelFactory {

    <V extends IView, VM extends IViewModel<V>> VM createViewModel(ViewHelper<?, ?, ?> viewHelper, IExtendView<?, ?, ?> extendView,String viewModelKey, Class<VM> viewModelType);

}
