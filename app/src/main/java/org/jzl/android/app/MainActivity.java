package org.jzl.android.app;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import org.jzl.android.app.databinding.ActivityMainBinding;
import org.jzl.android.app.vm.MainViewModel;
import org.jzl.android.mvvm.core.IViewHelper;
import org.jzl.android.mvvm.core.IViewHelperFactory;
import org.jzl.android.mvvm.view.AbstractMVVMActivity;
import org.jzl.android.mvvm.view.helper.ViewStubHelper;
import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.data.CommonlyModel;
import org.jzl.android.recyclerview.core.data.DefaultListDataProvider;
import org.jzl.android.recyclerview.core.data.Identifiable;
import org.jzl.android.recyclerview.core.data.SpanSize;
import org.jzl.android.recyclerview.core.diff.IDiffDispatcher;
import org.jzl.android.recyclerview.core.vh.CommonlyViewHolder;
import org.jzl.android.recyclerview.core.vh.DataBindingMatchPolicy;
import org.jzl.android.recyclerview.decorations.divider.DividerComponent;
import org.jzl.android.recyclerview.manager.group.GroupExpandable;
import org.jzl.android.recyclerview.manager.group.GroupManager;
import org.jzl.android.recyclerview.util.Logger;
import org.jzl.android.recyclerview.util.ScreenUtils;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.OnDataBlockChangedCallback;
import org.jzl.lang.util.CollectionUtils;
import org.jzl.lang.util.RandomUtils;
import org.jzl.router.core.JRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MainActivity extends AbstractMVVMActivity<MainActivity, MainViewModel, ActivityMainBinding> {
    private static final Logger log = Logger.logger(MainActivity.class);

    DefaultListDataProvider<CommonlyModel, CommonlyViewHolder> dataProvider = new DefaultListDataProvider<>();

    @Override
    public IViewHelper<MainActivity, MainViewModel> createViewHelper(IViewHelperFactory viewHelperFactory) {
        return viewHelperFactory.createViewHelper(this, R.layout.activity_main, BR.mainViewModel, MainViewModel.class);
    }

    @Override
    public void initialise(@NonNull ActivityMainBinding viewDataBinding, @NonNull MainViewModel viewModel) {
        Configuration<CommonlyModel, CommonlyViewHolder> configuration = Configuration.<CommonlyModel>builder()
                .enableEmptyLayout()
                .createEmptyLayoutItemView(R.layout.item_empty)

                .enableGroupManager()
                .setGroupHelper((groupManager, data) -> {
                    Material material = data.getData();
                    groupManager.group(material.groupId).getGroupContent().add(data);
                })
                .createItemView(R.layout.item_material, 1)
                .createItemView(R.layout.item_material_large, 2)
                .createItemView(R.layout.item_title, 3)
//                .setDataClassifier((dataProvider1, position) -> position % 3 == 0 ? 2 : 1)
                .component(DividerComponent.of(ScreenUtils.dip2px(this, 12)))
                .dataBindingByItemTypes((configuration1, holder, data) -> {
                    holder.getViewBinder().setImageResource(R.id.iv_icon, data.<Material>getData().icon);
                }, 1)
                .dataBindingByItemTypes((configuration1, holder, data) -> {
                    holder.getViewBinder().setImageResource(R.id.iv_icon_large, data.<Material>getData().icon);
                }, 2)
                .dataBindingByItemTypes((configuration1, holder, data) -> {
                    holder.getViewBinder().setText(R.id.tv_test, data.<GroupHeader>getData().text);
                }, 3)
                .addOnItemClickListener((configuration1, viewHolder, data) -> {
                    GroupExpandable<CommonlyModel> group = configuration1.getGroupManager().group(data.<GroupHeader>getData().groupId);
                    group.toggleExpand();
                }, DataBindingMatchPolicy.ofItemTypes(3))
                .setLayoutManagerFactory((context, recyclerView) -> new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false))
                .setDataProvider(dataProvider)
                .build(viewDataBinding.vpTest);
        configuration.getEmptyLayoutManager().addEmptyLayoutCallback(isEmpty -> {
            log.i("isEmpty -> " + isEmpty);
        });

        configuration.getLayoutManagerHelper().setSpanSizeLookup((data, spanCount, position) -> position % 3 == 0 ? spanCount : 1);

        IDiffDispatcher<CommonlyModel, CommonlyViewHolder> diffDispatcher = configuration.getDiffDispatcher();
        GroupManager<CommonlyModel, CommonlyViewHolder> groupManager = configuration.getGroupManager();
//
//        GroupExpandable<CommonlyModel> one = groupManager.group(0);
//        one.getGroupHeader().add(CommonlyModel.of(new GroupHeader(0, "one"), 3, SpanSize.ALL));
//
//        GroupExpandable<CommonlyModel> two = groupManager.group(1);
//        two.getGroupHeader().add(CommonlyModel.of(new GroupHeader(1, "two"), 3, SpanSize.ALL));
//
        viewDataBinding.btnAdd1.setOnClickListener(v -> {
            GroupExpandable<CommonlyModel> group = groupManager.group(RandomUtils.random(10));
            if (CollectionUtils.isEmpty(group.getGroupHeader())) {
                group.getGroupHeader().add(CommonlyModel.of(new GroupHeader(group.getGroupId(), String.valueOf(group.getGroupId())), 3, SpanSize.ALL));
            }
            List<CommonlyModel> materials = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                materials.add(CommonlyModel.of(new Material(R.mipmap.test, group.getGroupId()), 1));
            }
            group.getGroupContent().addAll(materials);
            log.i(dataProvider.size() + "");
        });
        viewDataBinding.btnAdd2.setOnClickListener(v -> dataProvider.clear());
        viewDataBinding.btnAdd3.setOnClickListener(v -> {
            JRouter.getInstance().navigation(this, "/test", postcard -> {
            });
        });
//
//        viewDataBinding.btnAdd3.setOnClickListener(v -> {
//            List<CommonlyModel> materials = new ArrayList<>();
//            for (int i = 0; i < 4; i++) {
//                materials.add(CommonlyModel.of(new Material(R.mipmap.test, RandomUtils.random(2)), 1));
//            }
//            groupManager.addAll(materials);
//        });
//
//        log.i("" + dataProvider);
//        log.i("" + dataProvider.getDataBlocks());

        dataProvider.addOnDataBlockChangedCallback(new OnDataBlockChangedCallback<CommonlyModel, DataBlockProvider<CommonlyModel>>() {
            @Override
            public void onChanged(DataBlockProvider<CommonlyModel> sender) {
                log.i("onChanged");
            }

            @Override
            public void onItemRangeChanged(DataBlockProvider<CommonlyModel> sender, int positionStart, int itemCount) {
                log.i("onItemRangeChanged -> " + positionStart + "," + itemCount);
            }

            @Override
            public void onItemRangeInserted(DataBlockProvider<CommonlyModel> sender, int positionStart, int itemCount) {
                log.i("onItemRangeInserted -> " + positionStart + "," + itemCount);
            }

            @Override
            public void onItemRangeMoved(DataBlockProvider<CommonlyModel> sender, int fromPosition, int toPosition) {
                log.i("onItemRangeMoved -> " + fromPosition + "," + toPosition);
            }

            @Override
            public void onItemRangeRemoved(DataBlockProvider<CommonlyModel> sender, int positionStart, int itemCount) {
                log.i("onItemRangeRemoved -> " + positionStart + "," + itemCount);
            }
        });
    }

    public static class Material implements Identifiable {

        private static final AtomicLong ID = new AtomicLong(0);

        private final long id;
        public final int groupId;

        @DrawableRes
        public final int icon;

        public Material(int icon, int groupId) {
            this.icon = icon;
            this.groupId = groupId;
            this.id = ID.incrementAndGet();
        }

        @Override
        public long getId() {
            return id;
        }
    }

    public static class GroupHeader {
        public final int groupId;
        public final String text;

        public GroupHeader(int groupId, String text) {
            this.text = text;
            this.groupId = groupId;
        }

        @Override
        @NonNull
        public String toString() {
            return "GroupHeader{" +
                    "groupId=" + groupId +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

}