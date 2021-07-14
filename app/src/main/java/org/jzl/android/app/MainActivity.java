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
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.DataBlockProviders;
import org.jzl.android.recyclerview.v3.core.IBindPolicy;
import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.IMatchPolicy;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewFactory;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.diff.IDiffDispatcher;
import org.jzl.android.recyclerview.v3.core.diff.IItemCallback;
import org.jzl.android.recyclerview.v3.core.layout.IEmptyLayoutManager;
import org.jzl.android.recyclerview.v3.core.layout.ILayoutManagerFactory;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.android.recyclerview.v3.core.plugins.SelectPlugin;
import org.jzl.android.recyclerview.v3.model.UniversalModel;
import org.jzl.lang.util.RandomUtils;
import org.jzl.lang.util.StringRandomUtils;

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
        DataBlockProvider<UniversalModel> dataBlockProvider = DataBlockProviders.dataBlockProvider();
        IEmptyLayoutManager<UniversalModel, IViewHolder> emptyLayoutManager = IEmptyLayoutManager.<UniversalModel, IViewHolder, MyTestViewHolder>of(IEmptyLayoutManager.DEFAULT_EMPTY_LAYOUT_ITEM_VIEW_TYPE, R.layout.layout_empty, UniversalModel.build("空的").build(), (options, itemView, itemViewType) -> new MyTestViewHolder(itemView.findViewById(R.id.tv_test)), (context, viewHolder, data) -> {
            viewHolder.textView.setText(String.valueOf(data.getData()));
        });
        IDiffDispatcher<UniversalModel, IViewHolder> diffDispatcher = IDiffDispatcher.of(new IItemCallback<UniversalModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull UniversalModel oldItem, @NonNull UniversalModel newItem) {
                return oldItem.getData().equals(newItem.getData());
            }

            @Override
            public boolean areContentsTheSame(@NonNull UniversalModel oldItem, @NonNull UniversalModel newItem) {
                return false;
            }

            @Override
            public Object getChangePayload(@NonNull UniversalModel oldItem, @NonNull UniversalModel newItem) {
                return "null";
            }
        });
        SelectPlugin<UniversalModel, IViewHolder> selectPlugin = SelectPlugin.of(SelectPlugin.SelectMode.SINGLE, (context, viewHolder, data) -> {
            context.getItemView().setBackgroundColor(data.isChecked() ? 0xff00ff00 : 0xffffffff);
        }, IMatchPolicy.ofItemTypes(0));
        IConfiguration<UniversalModel, IViewHolder> rootConfiguration = IConfiguration.<UniversalModel, IViewHolder>builder((options, itemView, itemViewType) -> new MyTestViewHolder(itemView.findViewById(R.id.tv_test)))
                .setDataProvider(dataBlockProvider)
                .enableAutomaticNotification()
                .plugin(emptyLayoutManager)
                .plugin(diffDispatcher)
                .plugin(selectPlugin)
                .registered(new TestMode(), target -> String.valueOf(target.getData()))
                .registered(new Test2(), target -> Integer.parseInt(target.getData().toString()))
                .build(dataBinding.vpTest);

        dataBinding.btnAdd3.setOnClickListener(v -> rootConfiguration.layoutManager(ILayoutManagerFactory.gridLayoutManager(2)));
        dataBinding.btnAdd2.setOnClickListener(v -> emptyLayoutManager.updateEmptyLayoutData(randomUniversalModel()));
        dataBinding.btnAdd1.setOnClickListener(v -> {
            diffDispatcher.submitList(
                    Arrays.asList(
                            randomUniversalModel(),
                            randomUniversalModel(),
                            randomUniversalModel(),
                            randomUniversalModel(),
                            randomUniversalModel()
                    )
            );
            Log.i("AdapterObservable", String.valueOf(dataBlockProvider));
        });
        dataBinding.btnAdd4.setOnClickListener(v -> dataBlockProvider.clear());
    }

    private UniversalModel randomUniversalModel() {
        return UniversalModel
                .build(StringRandomUtils.randomNumber(5))
                .setItemViewType(RandomUtils.random(2))
                .build();
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

    public static class TestMode implements IModule<String, MyTestViewHolder> {

        @NonNull
        @Override
        public IOptions<String, MyTestViewHolder> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<String> dataGetter) {
            return configuration
                    .options(this, (options, itemView, itemViewType) -> new MyTestViewHolder(itemView.findViewById(R.id.tv_test)), dataGetter)
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(0), 10)
                    .dataBindingByItemViewTypes((context, viewHolder, data) -> viewHolder.textView.setText(data), 0)
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
                    .options(this, (options, itemView, itemViewType) -> new MyTestViewHolder(itemView.findViewById(R.id.tv_test)), dataGetter)
                    .createItemView(IViewFactory.of(R.layout.item_title), IMatchPolicy.ofItemTypes(1), 10)
                    .dataBinding((context, viewHolder, data) -> viewHolder.textView.setText(String.valueOf("test2 => " + (data * data))), IBindPolicy.ofItemViewTypes(1), 0)
                    .build();
        }
    }

}