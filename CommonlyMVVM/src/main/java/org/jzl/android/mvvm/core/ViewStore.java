package org.jzl.android.mvvm.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;

import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ViewStore<V extends IExtendView<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding> implements
        IViewHelperFactory, IViewModelStoreOwner {

    private static final IKeyGenerator DEFAULT_KEY_GENERATOR = (parentKey, extendView, viewModelType) -> parentKey + ":" + viewModelType.getCanonicalName();
    private final HashSet<String> keys = new HashSet<>();
    private final Set<String> unmodifiableKeys = Collections.unmodifiableSet(keys);
    private final IKeyGenerator keyGenerator;
    private ViewModelProvider viewModelProvider;
    private IViewHelper<V, VM> viewHelper;
    private IViewModelStore viewModelStore;
    private VM viewModel;
    private VDB viewDataBinding;
    private String defaultViewModelKeyPrefix;

    public ViewStore() {
        this(DEFAULT_KEY_GENERATOR);
    }

    public ViewStore(IKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public <A extends Activity & IExtendView<V, VM, VDB>> void setContentView(A activity, @Nullable Bundle savedInstanceState) {
        setContentView(activity, savedInstanceState, DataBindingUtil.getDefaultComponent());
    }

    public <A extends Activity & IExtendView<V, VM, VDB>> void setContentView(A activity, @Nullable Bundle savedInstanceState, DataBindingComponent bindingComponent) {
        preBind(activity);
        bind(activity, DataBindingUtil.setContentView(activity, viewHelper.getLayoutId(), bindingComponent));
    }

    public View inflate(IExtendView<V, VM, VDB> extendView, @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflate(extendView, inflater, container, savedInstanceState, DataBindingUtil.getDefaultComponent());
    }

    public View inflate(IExtendView<V, VM, VDB> extendView, @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, DataBindingComponent bindingComponent) {
        preBind(extendView);
        VDB viewDataBinding = DataBindingUtil.inflate(inflater, viewHelper.getLayoutId(), container, false, bindingComponent);
        bind(extendView, viewDataBinding);
        return viewDataBinding.getRoot();
    }

    public View bind(IExtendView<V, VM, VDB> extendView, View root) {
        return bind(extendView, root, DataBindingUtil.getDefaultComponent());
    }

    public View bind(IExtendView<V, VM, VDB> extendView, View root, DataBindingComponent bindingComponent) {
        preBind(extendView);
        VDB viewDataBinding = DataBindingUtil.bind(root, bindingComponent);
        Objects.requireNonNull(viewDataBinding, "viewDataBinding is null");
        bind(extendView, viewDataBinding);
        return viewDataBinding.getRoot();
    }

    protected void bind(IExtendView<V, VM, VDB> extendView, VDB viewDataBinding) {
        this.viewDataBinding = viewDataBinding;
        viewDataBinding.setLifecycleOwner(extendView);
        if (extendView instanceof IPreBindingView) {
            ((IPreBindingView) extendView).onPreBinding();
        }
        this.viewModel = this.createVariableViewModel(defaultViewModelKeyPrefix, viewHelper.getVariableId(), viewHelper.getViewModelType(), true, viewModel -> extendView.initialise(viewDataBinding, viewModel));
    }

    protected void preBind(IExtendView<V, VM, VDB> extendView) {
        this.viewHelper = getViewHelper(extendView);
        this.defaultViewModelKeyPrefix = keyGenerator.generate("", extendView, viewHelper.getViewModelType());
        this.viewModelProvider = ViewModelProviders.of(extendView, viewHelper.getViewModelFactory());
    }

    public IViewHelper<V, VM> getViewHelper(IExtendView<V, VM, VDB> extendView) {
        if (ObjectUtils.isNull(viewHelper)) {
            viewHelper = extendView.createViewHelper(this);
        }
        return viewHelper;
    }

    public V getView() {
        return viewHelper.getView();
    }

    public VDB getViewDataBinding() {
        return viewDataBinding;
    }

    @Nullable
    public VM getViewModel() {
        return viewModel;
    }

    public void bindVariable(int variableId, Object value) {
        this.viewDataBinding.setVariable(variableId, value);
    }

    public <VM1 extends IViewModel> VM1 bindVariableViewModel(int variableId, Class<VM1> viewModelType) {
        return bindVariableViewModel(keyGenerator.generate(defaultViewModelKeyPrefix, getView(), viewModelType), variableId, viewModelType);
    }

    public <VM1 extends IViewModel> VM1 bindVariableViewModel(String key, int variableId, Class<VM1> viewModelType) {
        return createVariableViewModel(key, variableId, viewModelType, true, null);
    }

    private void bindViewModelVariable(boolean isBindVariable, int variableId, IViewModel viewModel) {
        if (isBindVariable) {
            if (viewModel instanceof IViewModelBindVariableOwner) {
                bindVariable(variableId, ((IViewModelBindVariableOwner) viewModel).getBindVariable());
            } else {
                bindVariable(variableId, viewModel);
            }
        }
    }

    public <VM1 extends IViewModel> VM1 getViewModel(Class<VM1> viewModelType) {
        return viewModelProvider.get(keyGenerator.generate(defaultViewModelKeyPrefix, getView(), viewModelType), viewModelType);
    }

    public <VM1 extends IViewModel> VM1 getViewModel(String key, Class<VM1> viewModelType) {
        return viewModelProvider.get(key, viewModelType, false);
    }

    public <VM1 extends IViewModel> VM1 createViewModel(Class<VM1> viewModelType) {
        return createViewModel(keyGenerator.generate(defaultViewModelKeyPrefix, getView(), viewModelType), viewModelType);
    }

    public <VM1 extends IViewModel> VM1 createViewModel(String key, Class<VM1> viewModelType) {
        return createVariableViewModel(key, -1, viewModelType, false, null);
    }

    private <VM1 extends IViewModel> VM1 createVariableViewModel(String key, int variableId, Class<VM1> viewModelType, boolean isBindVariable, Consumer<VM1> initialise) {
        VM1 viewModel;
        if (this.viewModelProvider.containsViewModel(key)) {
            viewModel = this.viewModelProvider.get(key, viewModelType);
            bindViewModelVariable(isBindVariable, variableId, viewModel);
            if (ObjectUtils.nonNull(initialise)) {
                initialise.accept(viewModel);
            }
        } else {
            keys.add(key);
            viewModel = this.viewModelProvider.get(key, viewModelType, true);
            viewModel.bind(getView());
            bindViewModelVariable(isBindVariable, variableId, viewModel);
            if (ObjectUtils.nonNull(initialise)) {
                initialise.accept(viewModel);
            }
            viewModel.initialise();
        }
        return viewModel;
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel> IViewHelper<V1, VM1> createViewHelper(V1 view, int layoutId, int variableId, Class<VM1> viewModelType) {
        return ViewHelper.of(view, layoutId, variableId, viewModelType);
    }

    @Override
    public <V1 extends IView, VM1 extends IViewModel> IViewHelper<V1, VM1> createViewHelper(V1 view, int layoutId, int variableId, Class<VM1> viewModelType, IViewModelFactory viewModelFactory) {
        return ViewHelper.of(view, layoutId, variableId, viewModelType, viewModelFactory);
    }

    @Override
    public <V1 extends IView, VM1 extends IViewModel> IViewHelper<V1, VM1> createViewHelper(Application application, V1 view, int layoutId, int variableId, Class<VM1> viewModelType) {
        return ViewHelper.of(view, layoutId, variableId, viewModelType, new ViewModelProviders.AndroidViewModelViewModelFactory(application));
    }

    @NonNull
    @Override
    public IViewModelStore getTargetViewModelStore() {
        if (ObjectUtils.isNull(viewModelStore)) {
            V view = getView();
            IExtendView<?, ?, ?> extendView = view.getParentContainerView();
            if (view != extendView) {
                this.viewModelStore = extendView.getTargetViewModelStore();
            } else {
                this.viewModelStore = new ViewModelStore();
            }
        }
        return viewModelStore;
    }

    public final Set<String> getKeys() {
        return unmodifiableKeys;
    }

    public void onDestroy() {
        V view = getView();
        if (view == view.getParentContainerView()) {
            if (ObjectUtils.nonNull(viewModelStore)) {
                this.viewModelStore.clear();
            }
        } else {
            ForeachUtils.each(this.keys, target -> {
                IViewModel viewModel = this.viewModelStore.remove(target);
                if (ObjectUtils.nonNull(viewModel)) {
                    viewModel.unbind();
                }
            });
        }
        if (ObjectUtils.nonNull(viewDataBinding)){
            viewDataBinding.unbind();
            viewDataBinding = null;
        }
    }

}
