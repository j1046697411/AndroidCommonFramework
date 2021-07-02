package org.jzl.android.app;

import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jzl.android.app.databinding.ActivityMainBinding;
import org.jzl.android.app.vm.MainViewModel;
import org.jzl.android.app.vm.TestDialogViewModel;
import org.jzl.android.mvvm.IViewBindingHelper;
import org.jzl.android.mvvm.IViewBindingHelperFactory;
import org.jzl.android.mvvm.MVVM;
import org.jzl.android.mvvm.view.core.AbstractMVVMActivity;
import org.jzl.android.recyclerview.util.Logger;
import org.jzl.android.recyclerview.v3.core.IBindPolicy;
import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IDataProvider;
import org.jzl.android.recyclerview.v3.core.IMatchPolicy;
import org.jzl.android.recyclerview.v3.core.IModule;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewFactory;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AbstractMVVMActivity<MainActivityView, MainViewModel, ActivityMainBinding> implements MainActivityView {

    public static final Logger log = Logger.logger(AbstractMVVMActivity.class);

    @NonNull
    @Override
    public IViewBindingHelper<MainActivityView, MainViewModel> createViewBindingHelper(IViewBindingHelperFactory factory) {
        return factory.createViewBindingHelper(this, R.layout.activity_main, BR.mainViewModel, MainViewModel.class);
    }

    @Override
    public void initialize(@NonNull ActivityMainBinding dataBinding, MainViewModel viewModel) {
        MyList list = new MyList();
        list.addAll(Arrays.asList(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
        ));
        IConfiguration.<String, IViewHolder>builder()
                .setDataClassifier((configuration, dataProvider, position) -> {
                    return  position % 3;
                })
                .setDataProvider(list)
                .registered(new TestMode())
                .registered(new Test2(), Integer::parseInt)
                .build(dataBinding.vpTest);

    }

    @Override
    public void unbind() {
        super.unbind();
        log.i("activity => unbind");
    }

    @Override
    public void showDialog() {
        MVVM.dialog(this, R.layout.dialog_test, BR.testDialogViewModel, TestDialogViewModel.class, (view, viewDataBinding, viewModel1) -> {
            log.i("dialog => initialize");
        }).show(this, "dialog");
    }

    public static class TestMode implements IModule<String, TestMode.MyTestViewHolder> {

        @NonNull
        @Override
        public IOptions<String, MyTestViewHolder> setup(@NonNull IConfiguration<?, ?> configuration) {
            return configuration
                    .options(this, (options, itemView, itemViewType) -> {
                        return new MyTestViewHolder(itemView.findViewById(R.id.tv_test));
                    })
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(0), 10)
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(1), 10)
                    .dataBinding((context, viewHolder, data) -> {
                        viewHolder.textView.setText(String.valueOf("test => " + data));
                    }, IBindPolicy.ofItemViewTypes(0, 1), 0)
                    .build();
        }

        public static class MyTestViewHolder implements IViewHolder {
            final TextView textView;

            public MyTestViewHolder(TextView textView) {
                this.textView = textView;
            }
        }
    }

    public static class Test2 implements IModule<Integer, TestMode.MyTestViewHolder> {

        @NonNull
        @Override
        public IOptions<Integer, TestMode.MyTestViewHolder> setup(@NonNull IConfiguration<?, ?> configuration) {
            return configuration
                    .options(this, (options, itemView, itemViewType) -> {
                        return new TestMode.MyTestViewHolder(itemView.findViewById(R.id.tv_test));
                    })
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(0), 15)
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(2), 10)
                    .dataBinding((context, viewHolder, data) -> {
                        viewHolder.textView.setText(String.valueOf("test2 => " + (data * data)));
                    }, IBindPolicy.ofItemViewTypes(0, 2), 0)
                    .build();
        }
    }

    private static class MyList extends ArrayList<String> implements IDataProvider<String> {
    }

}