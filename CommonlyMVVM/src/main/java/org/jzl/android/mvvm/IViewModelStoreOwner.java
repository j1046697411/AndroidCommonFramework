package org.jzl.android.mvvm;

/**
 * IViewModelStore 所有者接口，提供一个获取 IViewModelStore 对象的方法
 */
public interface IViewModelStoreOwner {

    IViewModelStore getSelfViewModelStore();
    
}
