package org.jzl.android.mvvm;

import java.util.Set;

public interface IViewModelStore {

    void observe(IViewModelStore.Observer observer);

    void unobserved(IViewModelStore.Observer observer);

    boolean containsKey(String key);

    void put(String key, IViewModel<?> viewModel);

    <V extends IView, VM extends IViewModel<V>> VM getViewModel(String key);

    <V extends IView, VM extends IViewModel<V>> VM getViewModel(Class<VM> viewModelType);

    <V extends IView, VM extends IViewModel<V>> VM getViewModel(String key, Class<VM> viewModelType);

    void unbinds(Set<String> keys);

    void unbind(String key);

    Set<String> getKeys();

    void clear();

    public interface Observer {

        void onBindViewModel(IViewModelStore viewModelStore, String key, IViewModel<?> viewModel);

        void onUnbindViewModel(IViewModelStore viewModelStore, String key, IViewModel<?> viewModel);

    }

}
