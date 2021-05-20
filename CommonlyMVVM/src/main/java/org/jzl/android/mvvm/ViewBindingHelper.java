package org.jzl.android.mvvm;

import androidx.annotation.NonNull;

import org.jzl.lang.util.ObjectUtils;

public class ViewBindingHelper<V extends IView, VM extends IViewModel<V>> implements IViewBindingHelper<V, VM> {

    private final int layoutResId;
    private final int variableId;
    private final V view;
    private final Class<VM> viewModelType;
    private final IViewModelFactory viewModelFactory;
    private final ViewModelProvider<V, VM> viewModelProvider;
    private IViewModelStore viewModelStore;

    public ViewBindingHelper(
            int layoutResId,
            int variableId,
            V view,
            Class<VM> viewModelType,
            IViewModelFactory viewModelFactory,
            ViewModelProvider<V, VM> viewModelProvider,
            IViewModelStore viewModelStore
    ) {
        this.layoutResId = layoutResId;
        this.variableId = variableId;
        this.view = view;
        this.viewModelType = viewModelType;
        this.viewModelFactory = viewModelFactory;
        this.viewModelProvider = viewModelProvider;
        this.viewModelStore = viewModelStore;
    }

    @Override
    public String getViewModelKey() {
        return viewModelType.getSimpleName();
    }

    @Override
    public int getLayoutResId() {
        return layoutResId;
    }

    @Override
    public int getVariableId() {
        return variableId;
    }

    @NonNull
    @Override
    public V getView() {
        return view;
    }

    @NonNull
    @Override
    public Class<VM> getViewModelType() {
        return viewModelType;
    }

    @NonNull
    @Override
    public IViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    @NonNull
    @Override
    public ViewModelProvider<V, VM> getViewModelProvider() {
        return viewModelProvider;
    }

    @Override
    public IViewModelStore getSelfViewModelStore() {
        return viewModelStore;
    }

    @Override
    public IViewModelStore getRealViewModelStore(@NonNull IExtendView<V, VM, ?> extendView) {
        if (ObjectUtils.nonNull(viewModelStore)) {
            return viewModelStore;
        }
        if (extendView.getParentContainerView() == extendView) {
            this.viewModelStore = new ViewModelStore();
            return viewModelStore;
        } else {
            return extendView.getParentContainerView().getSelfViewModelStore();
        }
    }

    @Override
    public void unbind() {
        if (ObjectUtils.nonNull(viewModelStore)) {
            viewModelStore.clear();
        }
        if (ObjectUtils.nonNull(viewModelProvider)) {
            viewModelProvider.unbind();
        }
    }
}
