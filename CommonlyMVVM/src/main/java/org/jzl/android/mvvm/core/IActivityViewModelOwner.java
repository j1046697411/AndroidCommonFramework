package org.jzl.android.mvvm.core;

public interface IActivityViewModelOwner extends IViewModelStoreOwner {

    void register(IViewModelStore viewModelStore);

    void unregister(IViewModelStore viewModelStore);
}
