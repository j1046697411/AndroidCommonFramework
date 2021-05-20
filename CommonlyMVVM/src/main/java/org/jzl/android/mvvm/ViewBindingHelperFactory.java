package org.jzl.android.mvvm;

import androidx.annotation.NonNull;

public class ViewBindingHelperFactory implements IViewBindingHelperFactory {

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> IViewBindingHelper<V1, VM1> createViewBindingHelper(@NonNull V1 view, int layoutResId, int variableId, Class<VM1> viewModelType, IViewModelFactory viewModelFactory, ViewModelProvider<V1, VM1> viewModelProvider, IViewModelStore viewModelStore) {
        return new ViewBindingHelper<>(layoutResId, variableId, view, viewModelType, viewModelFactory, viewModelProvider, viewModelStore);
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> IViewBindingHelper<V1, VM1> createViewBindingHelper(@NonNull V1 view, int layoutResId, int variableId, Class<VM1> viewModelType, IViewModelFactory viewModelFactory, ViewModelProvider<V1, VM1> viewModelProvider) {
        return createViewBindingHelper(view, layoutResId, variableId, viewModelType, viewModelFactory, viewModelProvider, null);
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> IViewBindingHelper<V1, VM1> createViewBindingHelper(@NonNull V1 view, int layoutResId, int variableId, Class<VM1> viewModelType, IViewModelFactory viewModelFactory) {
        return createViewBindingHelper(view, layoutResId, variableId, viewModelType, viewModelFactory, new ViewModelProvider<>());
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> IViewBindingHelper<V1, VM1> createViewBindingHelper(@NonNull V1 view, int layoutResId, int variableId, Class<VM1> viewModel) {
        return createViewBindingHelper(view, layoutResId, variableId, viewModel, new IViewModelFactory() {
            @Override
            public <V extends IView, VM extends IViewModel<V>> VM createViewModel(ViewHelper<?, ?, ?> viewHelper, IExtendView<?, ?, ?> extendView, String viewModelKey, Class<VM> viewModelType) {
                try {
                    return viewModelType.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
