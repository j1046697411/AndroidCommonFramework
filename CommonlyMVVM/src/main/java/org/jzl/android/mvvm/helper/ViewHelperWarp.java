package org.jzl.android.mvvm.helper;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.IExtendView;
import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.router.DefaultRouter;
import org.jzl.android.mvvm.router.Router;

abstract class ViewHelperWarp<V extends IExtendView<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding> implements IViewHelper<V, VM, VDB>, IViewHelperFactory {

    private IViewHelper<V, VM, VDB> viewHelper;
    private VM viewModel;
    private VDB viewDataBinding;
    private Router router;

    @Override
    public void bind(@NonNull V view, @NonNull VDB viewDataBinding) {
        this.viewDataBinding = viewDataBinding;
        router.injection(view);
        viewModel = createViewModel();
        viewModel.bind(view);
        view.bindVariable(getVariableId(), viewModel);
        view.initialise(viewDataBinding, viewModel);
        viewHelper.bind(view, viewDataBinding);
    }

    public void bindVariable(int variableId, Object value) {
        router.injection(value);
        viewDataBinding.setVariable(variableId, value);
    }

    protected void preBind(@NonNull V view) {
        viewHelper = view.createViewHelper(this);
        router = new DefaultRouter(view);
    }

    @Override
    public int getLayoutId() {
        return viewHelper.getLayoutId();
    }

    @Override
    public int getVariableId() {
        return viewHelper.getVariableId();
    }

    @Override
    @NonNull
    public VM createViewModel() {
        return viewHelper.createViewModel();
    }

    public VM getViewModel() {
        return viewModel;
    }

    public VDB getViewDataBinding() {
        return viewDataBinding;
    }

    public Router getRouter() {
        return router;
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel, VDB1 extends ViewDataBinding> IViewHelper<V1, VM1, VDB1> createViewHelper(V1 view, int layoutId, int variableId, VM1 viewModel) {
        return SimpleViewHelper.of(layoutId, variableId, viewModel);
    }

    @NonNull
    @Override
    public <V1 extends IView, VM1 extends IViewModel, VDB1 extends ViewDataBinding> IViewHelper<V1, VM1, VDB1> createViewHelper(V1 view, int layoutId, int variableId, Class<VM1> viewModelType) {
        try {
            return SimpleViewHelper.of(layoutId, variableId, viewModelType.newInstance());
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("构造 view model 失败", e);
        }
    }

    @Override
    public void unbind() {
        viewModel.unbind();
        viewHelper.unbind();
        viewDataBinding.unbind();
    }
}
