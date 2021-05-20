package org.jzl.android.mvvm;

import org.jzl.lang.util.CollectionUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ViewModelStore implements IViewModelStore {

    private final CopyOnWriteArrayList<Observer> observers = new CopyOnWriteArrayList<>();
    private final Map<String, IViewModel<?>> viewModels = new HashMap<>();
    private final Set<String> keys = Collections.unmodifiableSet(viewModels.keySet());

    @Override
    public void observe(Observer observer) {
        if (ObjectUtils.isNull(observer) || observers.contains(observer)) {
            return;
        }
        this.observers.add(observer);
    }

    @Override
    public void unobserved(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public boolean containsKey(String key) {
        return viewModels.containsKey(key);
    }

    @Override
    public void put(String key, IViewModel<?> viewModel) {
        if (ObjectUtils.nonNull(viewModel)) {
            IViewModel<?> oldViewModel = viewModels.put(key, viewModel);
            if (ObjectUtils.nonNull(oldViewModel)) {
                notifyUnbindViewModel(key, oldViewModel);
            }
            notifyBindViewModel(key, viewModel);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V extends IView, VM extends IViewModel<V>> VM getViewModel(String key) {
        return (VM) viewModels.get(key);
    }

    @Override
    public <V extends IView, VM extends IViewModel<V>> VM getViewModel(Class<VM> viewModelType) {
        for (IViewModel<?> value : viewModels.values()) {
            if (viewModelType.isInstance(value)) {
                return viewModelType.cast(value);
            }
        }
        return null;
    }

    @Override
    public <V extends IView, VM extends IViewModel<V>> VM getViewModel(String key, Class<VM> viewModelType) {
        IViewModel<?> value = viewModels.get(key);
        if (viewModelType.isInstance(value)) {
            return viewModelType.cast(value);
        }
        return getViewModel(viewModelType);
    }

    @Override
    public Set<String> getKeys() {
        return keys;
    }

    @Override
    public void unbinds(Set<String> keys) {
        if (CollectionUtils.nonEmpty(keys)) {
            for (String key : keys) {
                unbind(key);
            }
        }
    }

    @Override
    public void unbind(String key) {
        IViewModel<?> viewModel = this.viewModels.remove(key);
        if (ObjectUtils.nonNull(viewModel)) {
            notifyUnbindViewModel(key, viewModel);
            viewModel.unbind();
        }
    }

    private void notifyBindViewModel(String key, IViewModel<?> viewModel) {
        for (Observer observer : observers) {
            observer.onBindViewModel(this, key, viewModel);
        }
    }

    private void notifyUnbindViewModel(String key, IViewModel<?> viewMode) {
        for (Observer observer : observers) {
            observer.onUnbindViewModel(this, key, viewMode);
        }
    }

    @Override
    public void clear() {
        for (IViewModel<?> viewModel : this.viewModels.values()) {
            viewModel.unbind();
        }
        viewModels.clear();
    }
}
