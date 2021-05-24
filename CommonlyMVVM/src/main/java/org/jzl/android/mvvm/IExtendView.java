package org.jzl.android.mvvm;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

/**
 * MVVM 框架层扩展接口，主要用对对框架提供框架所必须的接口和对象，并且该接口实现了 IViewModelStoreOwner 接口，
 * 用于对应提供 ViewModel 的管理
 *
 * @param <V>   MVVM 框架核心的view对象类型
 * @param <VM>  MVVM 框架核心的ViewModel对象类型
 * @param <VDB> 页面对应的ViewDataBinding对象类型
 */
public interface IExtendView<V extends IView, VM extends IViewModel<V>, VDB extends ViewDataBinding> extends IView, IViewModelStoreOwner {


    /**
     * 获取该view对象的父view对象，在最顶层MVVM框架层内该对象就是自身对象
     *
     * @return 父view对象
     */
    @NonNull
    IExtendView<?, ?, ?> getParentContainerView();

    /**
     * 用于创建绑定view所需要的基础数据对象对象，页面布局、变量id、ViewModel类型等基础的数据
     *
     * @param factory 创建IViewBindingHelper对象的工厂对象，用于快速创建IViewBindingHelper对象
     * @return IViewBindingHelper viewBinding数据对口对象
     */
    @NonNull
    IViewBindingHelper<V, VM> createViewBindingHelper(IViewBindingHelperFactory factory);

    /**
     * view层初始化接口
     *
     * @param dataBinding 页面的 ViewDataBinding 对象
     * @param viewModel   页面核心的ViewModel对象
     */
    void initialize(@NonNull VDB dataBinding, VM viewModel);

    /**
     * @return 获取页面核心的ViewModel对象
     */
    @NonNull
    VM getViewModel();

    /**
     * @return 页面的 ViewDataBinding 对象
     */

    @NonNull
    VDB getViewDataBinding();

    /**
     * @return 页面核心的View对象，该对象实现了IExtendView 接口的 activity / dialog 等对象, 或者是创建 IViewBindingHelper 对象时候传入的对象
     */

    @NonNull
    V getSelfView();

}
