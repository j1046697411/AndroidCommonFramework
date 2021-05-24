package org.jzl.android.mvvm;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * MVVM 框架中 ViewModel 提供者，向ViewModel层提供viewModel的创建 获取 以及绑定相关的方法
 */
public interface IViewModelProvider {

    /**
     * 获取 框架中已经存在的 ViewModel对象
     *
     * @param key           查找的key
     * @param viewModelType 查找的类型
     * @param <V1>          被查找的ViewModel 依赖的 View 类型
     * @param <VM1>         被查找的ViewModel 类型
     * @return 查找到的ViewModel 对象
     */

    @Nullable
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getViewModel(String key, Class<VM1> viewModelType);

    /**
     * 获取 框架中已经存在的 ViewModel对象
     *
     * @param viewModelType 查找的类型
     * @param <V1>          被查找的ViewModel 依赖的 View 类型
     * @param <VM1>         被查找的ViewModel 类型
     * @return 查找到的ViewModel 对象
     */

    @Nullable
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getViewModel(Class<VM1> viewModelType);

    /**
     * 创建一个新的ViewModel对象，该key已经存在对象的ViewModel对象会被先解绑
     *
     * @param key           创建的key
     * @param viewModelType 创建的类型
     * @param <V1>          被创建的ViewModel 依赖的 View 类型
     * @param <VM1>         被创建的ViewModel 类型
     * @return 查找到的ViewModel 对象
     */

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 createViewModel(V1 view, String key, Class<VM1> viewModelType);

    /**
     * 创建一个新的ViewModel对象，并且根据key的生成方法生成新的key, 该key如果已经存在ViewModel对象会被先解绑
     *
     * @param viewModelType 创建的类型
     * @param <V1>          被创建的ViewModel 依赖的 View 类型
     * @param <VM1>         被创建的ViewModel 类型
     * @return 查找到的ViewModel 对象
     */
    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 createViewModel(V1 view, Class<VM1> viewModelType);

    /**
     * 绑定并创建一个新的ViewModel对象, 该key如果已经存在ViewModel对象会被先解绑
     *
     * @param view          创建的ViewModel 依赖的View对象
     * @param variableId    变量id
     * @param viewModelKey  viewModel的Kye
     * @param viewModelType viewModel 的类型
     * @param <V1>          被创建的ViewModel 依赖的 View 类型
     * @param <VM1>         被创建的ViewModel 类型
     * @return 新的viewModel对象
     */

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 bindVariableViewModel(@NonNull V1 view, int variableId, String viewModelKey, Class<VM1> viewModelType);


    /**
     * 绑定并创建一个新的ViewModel对象，并且根据key的生成方法生成新的key, 该key如果已经存在ViewModel对象会被先解绑
     *
     * @param view          创建的ViewModel 依赖的View对象
     * @param variableId    变量id
     * @param viewModelType viewModel 的类型
     * @param <V1>          被创建的ViewModel 依赖的 View 类型
     * @param <VM1>         被创建的ViewModel 类型
     * @return 新的viewModel对象
     */
    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 bindVariableViewModel(@NonNull V1 view, int variableId, Class<VM1> viewModelType);

    /**
     * 绑定并获取一个ViewModel对象
     *
     * @param variableId    绑定的变量id
     * @param viewModelKey  viewModel的key
     * @param viewModelType viewModel 的类型
     * @param <V1>          被获取的ViewModel 依赖的 View 类型
     * @param <VM1>         被获取的ViewModel 类型
     * @return 获取的ViewModel
     */

    @Nullable
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getBindVariableViewModel(int variableId, String viewModelKey, Class<VM1> viewModelType);

    /**
     * 绑定并获取一个ViewModel对象
     *
     * @param variableId    绑定的变量id
     * @param viewModelType viewModel 的类型
     * @param <V1>          被获取的ViewModel 依赖的 View 类型
     * @param <VM1>         被获取的ViewModel 类型
     * @return 获取的ViewModel
     */
    @Nullable
    <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getBindVariableViewModel(int variableId, Class<VM1> viewModelType);

}
