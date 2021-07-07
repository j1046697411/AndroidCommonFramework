package org.jzl.android.app;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.IDataProvider;
import org.jzl.android.recyclerview.v3.core.IMatchPolicy;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewFactory;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.lang.util.StringUtils;

import java.util.ArrayList;

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
        for (int i = 1; i < 10000; i++) {
            list.add(String.valueOf(i));
        }
        IConfiguration.<String, IViewHolder>builder((options, itemView, itemViewType) -> new MyTestViewHolder(itemView.findViewById(R.id.tv_test)))
                .setDataClassifier((configuration, dataProvider, position) -> position % 3)
//                .createItemView(R.layout.item_title, 0)
                .setDataProvider(list)
                .registered(new TestMode())
                .registered(new Test2(), Integer::parseInt)
                .build(dataBinding.vpTest)
                .addOnClickItemViewListener((o, context) -> {
                    Toast.makeText(this, "root", Toast.LENGTH_SHORT).show();
                }, IBindPolicy.BIND_POLICY_ALL);

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

    public class TestMode implements IModule<String, MyTestViewHolder> {

        @NonNull
        @Override
        public IOptions<String, MyTestViewHolder> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<String> dataGetter) {
            return configuration
                    .addOnLongClickItemViewListener((options, v) -> {
                        Toast.makeText(MainActivity.this, "all -> 2", Toast.LENGTH_SHORT).show();
                        return true;
                    }, 2)
                    .options(this, (options, itemView, itemViewType) -> new MyTestViewHolder(itemView.findViewById(R.id.tv_test)), dataGetter)
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(0), 10)
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(1), 10)
                    .dataBinding((context, viewHolder, data) -> {
                        viewHolder.textView.setText(String.valueOf("test => " + data));
                    }, IBindPolicy.ofItemViewTypes(0, 1), 0)
                    .addOnClickItemViewListener((options, viewHolderOwner) -> {
                        Toast.makeText(MainActivity.this, "test => " + options.getDataGetter().getData(viewHolderOwner.getContext().getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }, IBindPolicy.ofItemViewTypes(0))
                    .build();
        }

    }

    public static class MyTestViewHolder implements IViewHolder {
        final TextView textView;

        public MyTestViewHolder(TextView textView) {
            this.textView = textView;
        }
    }

    public static class Test2 implements IModule<Integer, MyTestViewHolder> {

        @NonNull
        @Override
        public IOptions<Integer, MyTestViewHolder> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<Integer> dataGetter) {
            return configuration
                    .options(this, (options, itemView, itemViewType) -> {
                        return new MyTestViewHolder(itemView.findViewById(R.id.tv_test));
                    }, dataGetter)
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(0), 15)
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(2), 10)
                    .dataBinding((context, viewHolder, data) -> {
                        viewHolder.textView.setText(String.valueOf("test2 => " + (data * data)));
                    }, IBindPolicy.ofItemViewTypes(0, 2), 0)

                    .addViewAttachedToWindowListener((options, owner) -> {
                        Log.i("-Test-", "Attached -> " + StringUtils.toHexString(owner.hashCode()));
                    }, IBindPolicy.BIND_POLICY_ALL)
                    .addViewDetachedFromWindowListener((options, owner) -> {
                        Log.i("-Test-", "Detached -> " + StringUtils.toHexString(owner.hashCode()));
                    }, IBindPolicy.BIND_POLICY_ALL)
                    .addAttachedToRecyclerViewListener((recyclerView, options) -> {
                        Log.i("-Test-", "Attached -> recyclerView");
                    })
                    .addDetachedFromRecyclerViewListener((recyclerView, options) -> {
                        Log.i("-Test-", "Detached -> recyclerView");
                    })
                    .addViewRecycledListener((options, viewHolderOwner) -> {
                        Log.i("-Test-", "Recycled -> " + StringUtils.toHexString(viewHolderOwner.hashCode()));
                    }, IBindPolicy.BIND_POLICY_ALL)
                    .build();
        }
    }

    private static class MyList extends ArrayList<String> implements IDataProvider<String> {
    }

}