# CommonlyAdapter

## 导入方式
```groovy
implementation "org.jzl.android.recyclerview:commonly-recyclerview:0.0.13"
```

## 首次使用
```java_holder_method_tree
/**
* 创建数据提供者用于与数据交互，使用该类实现了List接口，使用方式和List一样
* 改类实现了自动更新 RecyclerView.Adapter 的方法，所有不需要在手动的更新了
**/
DefaultListDataProvider<Material, CommonlyViewHolder> dataProvider = new DefaultListDataProvider<>();
Configuration.<Material>builder()
    .createItemView(R.layout.item_material)//绑定数据布局页面
    .dataBindingByItemTypes((configuration1, holder, data) -> {
        holder.getViewBinder().setImageResource(R.id.iv_icon, data.icon);
    })//数据绑定接口
    .setDataProvider(dataProvider)//绑定数据到Configuration上
    .build(viewDataBinding.vpTest);//绑定RecyclerView或者ViewPager2
```

## 修改 RecyclerView.LayoutManager
布局管理器目前只对 RecyclerView 起作用，ViewPager2 无效
```java_holder_method_tree
DefaultListDataProvider<Material, CommonlyViewHolder> dataProvider = new DefaultListDataProvider<>();
Configuration.<Material>builder()
    .createItemView(R.layout.item_material)
    .dataBindingByItemTypes((configuration1, holder, data) -> {
        holder.getViewBinder().setImageResource(R.id.iv_icon, data.icon);
    })
    .setLayoutManagerFactory((context, recyclerView) -> {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    })// 创建时候修改
    .setDataProvider(dataProvider)
    .build(viewDataBinding.vpTest);

// 后期动态修改
configuration.getLayoutManagerHelper().gridLayoutManager(2, GridLayoutManager.VERTICAL);
```

## 多布局模式
```java_holder_method_tree
Configuration<Material, CommonlyViewHolder> configuration = Configuration.<Material>builder()
    .createItemView(R.layout.item_material, 1)//绑定第一种布局页面
    .createItemView(R.layout.item_material_large, 2)//绑定第二种布局页面
    .setDataClassifier((dataProvider1, position) -> position % 3 == 0 ? 2 : 1)//设置数据分类器，用于区分多类型布局数据
    .dataBindingByItemTypes((configuration1, holder, data) -> {
        holder.getViewBinder().setImageResource(R.id.iv_icon, data.icon);
    }, 1)// 第一种布局数据绑定
    .dataBindingByItemTypes((configuration1, holder, data) -> {
        holder.getViewBinder().setImageResource(R.id.iv_icon_large, data.icon);
    }, 2)// 第二种布局数据绑定
    .setLayoutManagerFactory((context, recyclerView) -> new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false))
    .setDataProvider(dataProvider)
    .build(viewDataBinding.vpTest);
```

## 添加分割线
```java_holder_method_tree
Configuration<Material, CommonlyViewHolder> configuration = Configuration.<Material>builder()
    .createItemView(R.layout.item_material, 1)
    .createItemView(R.layout.item_material_large, 2)
    .setDataClassifier((dataProvider1, position) -> position % 3 == 0 ? 2 : 1)
    .component(DividerComponent.of(ScreenUtils.dip2px(this, 12))) //添加分割线代码
    .dataBindingByItemTypes((configuration1, holder, data) -> {
        holder.getViewBinder().setImageResource(R.id.iv_icon, data.icon);
    }, 1)
    .dataBindingByItemTypes((configuration1, holder, data) -> {
        holder.getViewBinder().setImageResource(R.id.iv_icon_large, data.icon);
    }, 2)
    .setLayoutManagerFactory((context, recyclerView) -> new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false))
    .setDataProvider(dataProvider)
    .build(viewDataBinding.vpTest);

```
## 空布局的使用
```java_holder_method_tree
DefaultListDataProvider<Material, CommonlyViewHolder> dataProvider = new DefaultListDataProvider<>();
Configuration<Material, CommonlyViewHolder> configuration = Configuration.<Material>builder()
        .enableEmptyLayout()//启用空布局
        .createEmptyLayoutItemView(R.layout.item_empty) //设置空布局页面

        .createItemView(R.layout.item_material, 1)
        .createItemView(R.layout.item_material_large, 2)
        .setDataClassifier((dataProvider1, position) -> position % 3 == 0 ? 2 : 1)
        .component(DividerComponent.of(ScreenUtils.dip2px(this, 12)))
        .dataBindingByItemTypes((configuration1, holder, data) -> {
            holder.getViewBinder().setImageResource(R.id.iv_icon, data.icon);
        }, 1)
        .dataBindingByItemTypes((configuration1, holder, data) -> {
            holder.getViewBinder().setImageResource(R.id.iv_icon_large, data.icon);
        }, 2)
        .setLayoutManagerFactory((context, recyclerView) -> new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false))
        .setDataProvider(dataProvider)
        .build(viewDataBinding.vpTest);

//跳转布局占位空间
configuration.getLayoutManagerHelper().setSpanSizeLookup((data, spanCount, position) -> position % 3 == 0 ? spanCount : 1);
```
## DiffUtil 的使用
```java_holder_method_tree
Configuration<Material, CommonlyViewHolder> configuration = Configuration.<Material>builder()
        .enableEmptyLayout()
        .createEmptyLayoutItemView(R.layout.item_empty)

        .createItemView(R.layout.item_material, 1)
        .createItemView(R.layout.item_material_large, 2)
        .setDataClassifier((dataProvider1, position) -> position % 3 == 0 ? 2 : 1)
        .component(DividerComponent.of(ScreenUtils.dip2px(this, 12)))
        .dataBindingByItemTypes((configuration1, holder, data) -> {
            holder.getViewBinder().setImageResource(R.id.iv_icon, data.icon);
        }, 1)
        .dataBindingByItemTypes((configuration1, holder, data) -> {
            holder.getViewBinder().setImageResource(R.id.iv_icon_large, data.icon);
        }, 2)
        .setLayoutManagerFactory((context, recyclerView) -> new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false))
        .setDataProvider(dataProvider)
        //设置 itemCallback 用于Diff数据判断
        .setItemCallback(new DiffUtil.ItemCallback<Material>() {
            @Override
            public boolean areItemsTheSame(@NonNull Material oldItem, @NonNull Material newItem) {
                if (ObjectUtils.nonNull(oldItem) && ObjectUtils.nonNull(newItem)){
                    return oldItem.getId() == newItem.getId();
                }
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Material oldItem, @NonNull Material newItem) {
                return oldItem.icon == newItem.icon;
            }
        })
        .build(viewDataBinding.vpTest);
configuration.getLayoutManagerHelper().setSpanSizeLookup((data, spanCount, position) -> position % 3 == 0 ? spanCount : 1);

//获取diff调度员对象
IDiffDispatcher<Material, CommonlyViewHolder> diffDispatcher = configuration.getDiffDispatcher();
viewDataBinding.btnAdd.setOnClickListener(v -> {
    List<Material> materials = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
        materials.add(new Material(R.mipmap.test));
    }
    //提交需要更新的新数据
    diffDispatcher.submitList(materials);
});

```