package org.jzl.android.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * MVVM 框架中view层的基础接口，所有的view对象都需要实现或者继承实现了改接口的对象，
 * 该接口提供了MVVM view层必须的接口
 */
public interface IView extends LifecycleOwner, IVariableBinder {

    /**
     * <p>
     * 先说说为啥需要这样定义，首先这个view接口是提供给viewModel调用的基础view接口，
     * 在viewModel中需要经常用到上下文对象，所有这里的view接口就提供了一个上下文对象，
     * 保证了在viewModel中需要时候能获取到上下文。
     * </p>
     *
     * @return 上下文对象
     */

    Application getApplication();

    /**
     * <p>
     * LifecycleOwner接口继承下来的方法，方便在viewModel提供view的生命周期 和 LiveData 的使用
     * </p>
     *
     * @return 生命周期对象
     */
    @NonNull
    @Override
    Lifecycle getLifecycle();

    /**
     * <p>
     * IViewModelProvider 对象，主要作用是在viewModel中banding其他的viewModel，方便viewModel复用
     * </p>
     *
     * @return viewModel 提供者对象
     */
    @NonNull
    IViewModelProvider getViewModelProvider();

    /**
     * 属性变量绑定接口，对viewModel提供ViewDataBinding 的 变量绑定接口，方便在viewModel中绑定变量
     *
     * @param variableId BR 中变量id
     * @param value      需要绑定的属性值，该值如果实现了 IBindVariableOwner 接口，则采用接口方法返回的对象值绑定。
     */

    @Override
    void bindVariable(int variableId, @Nullable Object value);

    /**
     * view对象解绑接口，用于释放对应资源
     */

    void unbind();

}
