package org.jzl.android.mvvm.core;

import org.jzl.lang.util.ObjectUtils;

public final class ViewModelProvider {

    private static final String DEFAULT_VIEW_MODEL_KEY = "org.jzl.android.mvvm.core.ViewModelProvider";

    private final IViewModelStore viewModelStore;
    private final IViewModelFactory viewModelFactory;

    ViewModelProvider(IViewModelStore viewModelStore, IViewModelFactory viewModelFactory) {
        this.viewModelStore = viewModelStore;
        this.viewModelFactory = viewModelFactory;
    }

    public boolean containsViewModel(String key) {
        return viewModelStore.getKeys().contains(key);
    }

    public <VM extends IViewModel> VM get(Class<VM> viewModelType) {
        return get(viewModelType, true);
    }

    public <VM extends IViewModel> VM get(String key, Class<VM> viewModelType) {
        return get(key, viewModelType, true);
    }

    public <VM extends IViewModel> VM get(Class<VM> viewModelType, boolean autoCreatedViewModel) {
        return get(DEFAULT_VIEW_MODEL_KEY + ":" + viewModelType.getCanonicalName(), viewModelType, autoCreatedViewModel);
    }

    @SuppressWarnings("unchecked")
    public <VM extends IViewModel> VM get(String key, Class<VM> viewModelType, boolean autoCreatedViewModel) {
        IViewModel viewModel = viewModelStore.get(key);
        if (ObjectUtils.isNull(viewModel) && autoCreatedViewModel) {
            if (viewModelFactory instanceof IKeyViewModelFactory) {
                viewModel = ((IKeyViewModelFactory) viewModelFactory).create(key, viewModelType);
            } else {
                viewModel = viewModelFactory.create(viewModelType);
            }
            viewModelStore.put(key, viewModel);
        }
        return (VM) viewModel;
    }

}
