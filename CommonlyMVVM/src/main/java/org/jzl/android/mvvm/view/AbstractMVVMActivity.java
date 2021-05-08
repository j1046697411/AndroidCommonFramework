package org.jzl.android.mvvm.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import org.jzl.android.mvvm.core.IExtendView;
import org.jzl.android.mvvm.core.IPreBindingView;
import org.jzl.android.mvvm.core.IViewModel;
import org.jzl.android.mvvm.core.IViewModelStore;
import org.jzl.android.mvvm.core.ViewStore;
import org.jzl.android.mvvm.vm.ActivityViewModel;
import org.jzl.lang.util.ObjectUtils;

public abstract class AbstractMVVMActivity<V extends AbstractMVVMActivity<V, VM, VDB>, VM extends IViewModel, VDB extends ViewDataBinding>
        extends AppCompatActivity implements IExtendView<V, VM, VDB>, IPreBindingView {

    public static final String KEY_ACTIVITY_VIEW_MODEL = "org.jzl.android.mvvm.view.Activity::ActivityViewModel";

    protected final ViewStore<V, VM, VDB> viewStore = new ViewStore<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewStore.setContentView(this, savedInstanceState);
    }

    @Override
    public void onPreBinding() {
        bindActivityViewModel(createViewModel(KEY_ACTIVITY_VIEW_MODEL, ActivityViewModel.class));
    }

    protected void bindActivityViewModel(ActivityViewModel viewModel) {
        viewModel.finish.observe(this, activityFinishModel -> {
            if (ObjectUtils.nonNull(activityFinishModel)) {
                if (activityFinishModel.getResultCode() != RESULT_CANCELED) {
                    setResult(activityFinishModel.getResultCode(), activityFinishModel.getResultData());
                }
                finish();
            }
        });
    }

    @NonNull
    @Override
    public IViewModelStore getTargetViewModelStore() {
        return viewStore.getTargetViewModelStore();
    }

    @Override
    public VDB getViewDataBinding() {
        return viewStore.getViewDataBinding();
    }

    @Override
    public VM getViewModel() {
        return viewStore.getViewModel();
    }

    @Override
    public void bindVariable(int variableId, Object value) {
        viewStore.bindVariable(variableId, value);
    }

    @Override
    public <VM1 extends IViewModel> VM1 bindVariableViewModel(int variableId, Class<VM1> viewModelType) {
        return viewStore.bindVariableViewModel(variableId, viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 bindVariableViewModel(String key, int variableId, Class<VM1> viewModelType) {
        return viewStore.bindVariableViewModel(key, variableId, viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 getViewModel(String key, Class<VM1> viewModelType) {
        return viewStore.getViewModel(key, viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 getViewModel(Class<VM1> viewModelType) {
        return viewStore.getViewModel(viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 createViewModel(String key, Class<VM1> viewModelType) {
        return viewStore.createViewModel(key, viewModelType);
    }

    @Override
    public <VM1 extends IViewModel> VM1 createViewModel(Class<VM1> viewModelType) {
        return viewStore.createViewModel(viewModelType);
    }

    @Override
    public final IExtendView<?, ?, ?> getParentContainerView() {
        return this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewStore.onDestroy();
    }
}
