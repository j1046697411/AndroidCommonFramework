package org.jzl.android.mvvm;

import java.util.Set;

/**
 * 管理已经存在的ViewModel对象的接口，向外提供判断和获取已经放入的方法，
 * 并提供了监听对象监听ViewModel的绑定已经解绑的观察者接口
 */
public interface IViewModelStore {

    void observe(IViewModelStore.Observer observer);

    void unobserved(IViewModelStore.Observer observer);

    /**
     * 判断某个key是否存在
     *
     * @param key 需要判断的key
     * @return 存在与否
     */
    boolean containsKey(String key);

    /**
     * 存入的接口
     *
     * @param key       viewModel 的 key
     * @param viewModel 对应的ViewModel对象
     */
    void put(String key, IViewModel<?> viewModel);

    /**
     * 根据key获取对应的ViewModel
     *
     * @param key  key
     * @param <V>  被获取的ViewModel 依赖的 View 类型
     * @param <VM> 被获取的ViewModel 类型
     * @return 获取的viewModel 对象
     */
    <V extends IView, VM extends IViewModel<V>> VM getViewModel(String key);

    /**
     * 根据viewModelType获取对应的ViewModel
     *
     * @param viewModelType viewModelType
     * @param <V>           被获取的ViewModel 依赖的 View 类型
     * @param <VM>          被获取的ViewModel 类型
     * @return 获取的viewModel 对象
     */
    <V extends IView, VM extends IViewModel<V>> VM getViewModel(Class<VM> viewModelType);

    /**
     * 根据viewModelType 和 key 获取对应的ViewModel
     *
     * @param key           key
     * @param viewModelType viewModelType
     * @param <V>           被获取的ViewModel 依赖的 View 类型
     * @param <VM>          被获取的ViewModel 类型
     * @return 获取的viewModel 对象
     */
    <V extends IView, VM extends IViewModel<V>> VM getViewModel(String key, Class<VM> viewModelType);


    /**
     * 解绑keys 对应的 ViewModel
     *
     * @param keys 需要解绑的key的集合
     */
    void unbinds(Set<String> keys);

    /**
     * 解绑key 对应的ViewModel
     *
     * @param key 需要解绑的key
     */
    void unbind(String key);

    /**
     * 被管理的viewModel全部的keys
     *
     * @return keys
     */
    Set<String> getKeys();

    /**
     * 清理全部的ViewModel
     */
    void clear();

    /**
     * ViewMode 绑定和解绑的观察者接口
     */
    interface Observer {

        /**
         * 绑定ViewModel 时候的回调方法
         *
         * @param viewModelStore viewModelStore
         * @param key            绑定的ViewModel的key
         * @param viewModel      绑定的ViewModel的key
         */
        void onBindViewModel(IViewModelStore viewModelStore, String key, IViewModel<?> viewModel);

        /**
         * 解绑ViewModel 时候的回调方法
         *
         * @param viewModelStore viewModelStore
         * @param key            解绑的ViewModel的key
         * @param viewModel      解绑的ViewModel的key
         */
        void onUnbindViewModel(IViewModelStore viewModelStore, String key, IViewModel<?> viewModel);

    }

}
