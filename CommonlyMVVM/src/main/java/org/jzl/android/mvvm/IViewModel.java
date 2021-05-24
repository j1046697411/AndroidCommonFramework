package org.jzl.android.mvvm;

import androidx.annotation.NonNull;

/**
 * MVVM框架中， viewModel层的基础接口，所有的ViewModel都需要实现该接口
 *
 * @param <V> 该viewModel依赖的view层接口或者对象
 */

public interface IViewModel<V extends IView> {

    /**
     * viewModel 绑定view对象时调用的方法。
     *
     * @param view 依赖的View对象
     */

    void bind(@NonNull V view);

    /**
     * viewModel 对象初始化方法
     */
    void initialise();

    /**
     * viewModel 解绑方法
     */
    void unbind();

}
