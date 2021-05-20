package org.jzl.android.mvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.databinding.ViewDataBinding;

import org.jzl.lang.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

public class ViewModelProvider<V extends IView, VM extends IViewModel<V>> implements IViewModelProvider, IViewModelStore.Observer {

    private ViewHelper<V, VM, ?> viewHelper;
    private IExtendView<V, VM, ?> extendView;
    private IViewModelFactory viewModelFactory;
    private IViewModelStore viewModelStore;
    private final Set<String> keys = new HashSet<>();

    @NonNull
    public <VDB extends ViewDataBinding> VM bindViewModel(ViewHelper<V, VM, VDB> viewHelper, IExtendView<V, VM, VDB> extendView, VDB viewDataBinding) {
        preBind(viewHelper, extendView);
        IViewBindingHelper<V, VM> viewBindingHelper = viewHelper.getViewBindingHelper();
        VM viewModel = createViewModel(viewHelper.getView(), viewBindingHelper.getViewModelKey(), viewBindingHelper.getViewModelType(), vm -> extendView.initialize(viewDataBinding, vm));
        viewHelper.bindVariable(viewBindingHelper.getVariableId(), viewModel);
        return viewModel;
    }

    private void preBind(ViewHelper<V, VM, ?> viewHelper, IExtendView<V, VM, ?> extendView) {
        this.viewHelper = viewHelper;
        this.extendView = extendView;
        this.viewModelFactory = viewHelper.getViewModelFactory();
        this.viewModelStore = viewHelper.getSelfViewModelStore();
        this.viewModelStore.observe(this);
    }

    @Override
    @Nullable
    public <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getViewModel(String key, Class<VM1> viewModelType) {
        return viewModelStore.getViewModel(key, viewModelType);
    }

    @Override
    @Nullable
    public <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getViewModel(Class<VM1> viewModelType) {
        return viewModelStore.getViewModel(viewModelType);
    }

    @Override
    @NonNull
    public <V1 extends IView, VM1 extends IViewModel<V1>> VM1 createViewModel(V1 view, String key, Class<VM1> viewModelType) {
        return createViewModel(view, key, viewModelType, null);
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> VM1 createViewModel(V1 view, Class<VM1> viewModelType) {
        return createViewModel(view, viewHelper.generateViewModelKey(viewModelType), viewModelType);
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> VM1 bindVariableViewModel(@NonNull V1 view, int variableId, String viewModelKey, Class<VM1> viewModelType) {
        VM1 viewModel = createViewModel(view, viewModelKey, viewModelType);
        viewHelper.bindVariable(variableId, viewModel);
        return viewModel;
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> VM1 bindVariableViewModel(@NonNull V1 view, int variableId, Class<VM1> viewModelType) {
        VM1 viewModel = createViewModel(view, viewModelType);
        viewHelper.bindVariable(variableId, viewModel);
        return viewModel;
    }

    @Nullable
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getBindVariableViewModel(int variableId, String viewModelKey, Class<VM1> viewModelType) {
        VM1 viewModel = getViewModel(viewModelKey, viewModelType);
        viewHelper.bindVariable(variableId, viewModel);
        return viewModel;
    }

    @Nullable
    @Override
    public <V1 extends IView, VM1 extends IViewModel<V1>> VM1 getBindVariableViewModel(int variableId, Class<VM1> viewModelType) {
        VM1 viewModel = getViewModel(viewModelType);
        viewHelper.bindVariable(variableId, viewModel);
        return viewModel;
    }


    private <V1 extends IView, VM1 extends IViewModel<V1>> VM1 createViewModel(V1 view, String key, Class<VM1> viewModelTyp, Consumer<VM1> consumer) {
        VM1 viewModel = viewModelFactory.createViewModel(viewHelper, extendView, key, viewModelTyp);
        viewModel.bind(view);
        if (ObjectUtils.nonNull(consumer)) {
            consumer.accept(viewModel);
        }
        viewModel.initialise();
        this.viewModelStore.put(key, viewModel);
        this.keys.add(key);
        return viewModel;
    }

    public void unbind() {
        viewModelStore.unobserved(this);
        viewModelStore.unbinds(keys);
        keys.clear();
        this.viewHelper = null;
        this.viewModelStore = null;
        this.extendView = null;
        this.viewModelFactory = null;
    }

    @Override
    public void onBindViewModel(IViewModelStore viewModelStore, String key, IViewModel<?> viewModel) {
    }

    @Override
    public void onUnbindViewModel(IViewModelStore viewModelStore, String key, IViewModel<?> viewModel) {
        this.keys.remove(key);
    }
}
