package org.jzl.android.mvvm;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.ViewStubProxy;

import java.util.Objects;

/**
 *
 * MVVM 核心代码，用于控制整个MVVM框架viewModel的创建，绑定、初始化，以及释放
 * 以及 view层的绑定和初始化 以及释放等功能
 *
 * @param <V>   核心的View类型
 * @param <VM>  核心的ViewModel 类型
 * @param <VDB> 对应页面的ViewDataBinding
 */
public class ViewHelper<V extends IView, VM extends IViewModel<V>, VDB extends ViewDataBinding>
        implements IVariableBinder, IViewModelStoreOwner {

    private IViewBindingHelper<V, VM> viewBindingHelper;

    private IExtendView<V, VM, VDB> extendView;
    private VDB dataBinding;
    private VM viewModel;

    /**
     * activity 的入口方法
     *
     * @param activity           实现了 IExtendView  接口的activity对象
     * @param savedInstanceState 保存状态对象
     * @param <A>                activity 的类型
     */
    public <A extends Activity & IExtendView<V, VM, VDB>> void setContentView(@NonNull A activity, @Nullable Bundle savedInstanceState) {
        perBind(activity);
        bind(activity, viewBindingHelper, DataBindingUtil.setContentView(activity, viewBindingHelper.getLayoutResId()));
    }

    /**
     * 布局绑定的入口方法，适用于需要 inflate 布局的方式实现MVVM框架
     *
     * @param extendView     实现了 IExtendView 接口的容器对象View对象
     * @param layoutInflater 布局 layoutInflater
     * @param parent         父节点 View
     * @return inflate 的 View
     */

    public View inflate(@NonNull IExtendView<V, VM, VDB> extendView, @NonNull LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        perBind(extendView);
        VDB dataBinding = DataBindingUtil.inflate(layoutInflater, viewBindingHelper.getLayoutResId(), parent, false);
        bind(extendView, viewBindingHelper, dataBinding);
        return dataBinding.getRoot();
    }

    /**
     * ViewStubProxy 对象的绑定入口方法
     *
     * @param extendView    实现了 IExtendView 接口的容器对象View对象
     * @param viewStubProxy ViewStub 代理对象
     */

    public void inflate(@NonNull IExtendView<V, VM, VDB> extendView, @NonNull ViewStubProxy viewStubProxy) {
        perBind(extendView);
        ViewStub viewStub = viewStubProxy.getViewStub();
        Objects.requireNonNull(viewStub, "viewStub is null");
        if (!viewStubProxy.isInflated()) {
            viewStub.setLayoutResource(viewBindingHelper.getLayoutResId());
        }
        VDB dataBinding = DataBindingUtil.bind(viewStub.inflate());
        Objects.requireNonNull(dataBinding, "dataBinding is null");
        bind(extendView, viewBindingHelper, dataBinding);
    }

    /**
     * 绑定已经是view的MVVM框架的入口方法
     *
     * @param extendView 实现了 IExtendView 接口的容器对象View对象
     * @param view       容器的view对象
     */
    public void bind(@NonNull IExtendView<V, VM, VDB> extendView, @NonNull View view) {
        perBind(extendView);
        bind(extendView, viewBindingHelper, Objects.requireNonNull(DataBindingUtil.bind(view), "dataBinding is null"));
    }

    private void bind(@NonNull IExtendView<V, VM, VDB> extendView, @NonNull IViewBindingHelper<V, VM> viewBindingHelper, @NonNull VDB dataBinding) {
        dataBinding.setLifecycleOwner(extendView);
        this.dataBinding = dataBinding;
        this.extendView = extendView;
        if (extendView instanceof IPreBindingView) {
            ((IPreBindingView) extendView).onPreBinding();
        }
        ViewModelProvider<V, VM> viewModelProvider = viewBindingHelper.getViewModelProvider();
        this.viewModel = viewModelProvider.bindViewModel(this, extendView, dataBinding);
    }

    protected void perBind(@NonNull IExtendView<V, VM, VDB> extendView) {
        viewBindingHelper = extendView.createViewBindingHelper(new ViewBindingHelperFactory());
    }

    @Override
    public void bindVariable(int variableId, @Nullable Object value) {
        if (value instanceof IBindVariableOwner) {
            this.dataBinding.setVariable(variableId, ((IBindVariableOwner) value).getBindVariableValue());
        } else {
            this.dataBinding.setVariable(variableId, value);
        }
    }

    @NonNull
    public IViewBindingHelper<V, VM> getViewBindingHelper() {
        return viewBindingHelper;
    }

    @NonNull
    public V getView() {
        return viewBindingHelper.getView();
    }

    @NonNull
    public VDB getViewDataBinding() {
        return dataBinding;
    }

    @NonNull
    public VM getViewModel() {
        return viewModel;
    }

    @NonNull
    public IViewModelFactory getViewModelFactory() {
        return viewBindingHelper.getViewModelFactory();
    }

    @Override
    public IViewModelStore getSelfViewModelStore() {
        return viewBindingHelper.getRealViewModelStore(extendView);
    }

    /**
     *
     * @return
     */
    @NonNull
    public IViewModelProvider getViewModelProvider() {
        return viewBindingHelper.getViewModelProvider();
    }

    /**
     * 创建 viewModel 时，只传入了 viewModelType 时生成key的方法
     *
     * @param viewModel ViewModel 类型
     * @return 生成的key
     */
    public String generateViewModelKey(Class<?> viewModel) {
        return viewModel.getSimpleName();
    }

    /**
     * view 容器释放时调用的方法
     */
    public void unbind() {
        viewBindingHelper.unbind();
        dataBinding.unbind();
        extendView.unbind();
    }
}
