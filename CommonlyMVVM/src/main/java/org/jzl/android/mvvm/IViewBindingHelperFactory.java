package org.jzl.android.mvvm;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * 提供创建 IViewBindingHelper 对象的工厂接口
 */
public interface IViewBindingHelperFactory {

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> IViewBindingHelper<V1, VM1> createViewBindingHelper(@NonNull V1 view, @LayoutRes int layoutResId, int variableId, Class<VM1> viewModelType, IViewModelFactory viewModelFactory, ViewModelProvider<V1, VM1> viewModelProvider, IViewModelStore viewModelStore);

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> IViewBindingHelper<V1, VM1> createViewBindingHelper(@NonNull V1 view, @LayoutRes int layoutResId, int variableId, Class<VM1> viewModelType, IViewModelFactory viewModelFactory, ViewModelProvider<V1, VM1> viewModelProvider);

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> IViewBindingHelper<V1, VM1> createViewBindingHelper(@NonNull V1 view, @LayoutRes int layoutResId, int variableId, Class<VM1> viewModelType, IViewModelFactory viewModelFactory);

    @NonNull
    <V1 extends IView, VM1 extends IViewModel<V1>> IViewBindingHelper<V1, VM1> createViewBindingHelper(@NonNull V1 view, @LayoutRes int layoutResId, int variableId, Class<VM1> viewModel);

}
