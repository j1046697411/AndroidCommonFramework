package org.jzl.android.app;

import androidx.annotation.NonNull;

import org.jzl.android.app.databinding.ActivityMainBinding;
import org.jzl.android.app.vm.MainViewModel;
import org.jzl.android.app.vm.TestDialogViewModel;
import org.jzl.android.mvvm.IViewBindingHelper;
import org.jzl.android.mvvm.IViewBindingHelperFactory;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.IViewModelStore;
import org.jzl.android.mvvm.MVVM;
import org.jzl.android.mvvm.view.core.AbstractMVVMActivity;
import org.jzl.android.recyclerview.util.Logger;

public class MainActivity extends AbstractMVVMActivity<MainActivityView, MainViewModel, ActivityMainBinding> implements MainActivityView {

    public static final Logger log = Logger.logger(AbstractMVVMActivity.class);

    @NonNull
    @Override
    public IViewBindingHelper<MainActivityView, MainViewModel> createViewBindingHelper(IViewBindingHelperFactory factory) {
        return factory.createViewBindingHelper(this, R.layout.activity_main, BR.mainViewModel, MainViewModel.class);
    }

    @Override
    public void initialize(@NonNull ActivityMainBinding dataBinding, MainViewModel viewModel) {
        log.i("activity => initialize" );
        getSelfViewModelStore().observe(new IViewModelStore.Observer() {
            @Override
            public void onBindViewModel(IViewModelStore viewModelStore, String key, IViewModel<?> viewModel) {
                log.i("onBindViewModel => " + key);
            }

            @Override
            public void onUnbindViewModel(IViewModelStore viewModelStore, String key, IViewModel<?> viewModel) {
                log.i("onUnbindViewModel => " + key);
            }
        });
    }

    @Override
    public void unbind() {
        super.unbind();
        log.i("activity => unbind" );
    }

    @Override
    public void showDialog() {
        MVVM.dialog(this, R.layout.dialog_test, BR.testDialogViewModel, TestDialogViewModel.class, (view, viewDataBinding, viewModel1) -> {
            log.i("dialog => initialize" );
        }).show(this, "dialog");
    }
}