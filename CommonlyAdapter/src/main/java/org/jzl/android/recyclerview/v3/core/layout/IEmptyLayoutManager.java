package org.jzl.android.recyclerview.v3.core.layout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IDataBinder;
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IPlugin;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderFactory;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.android.recyclerview.v3.core.vh.DefaultViewHolderFactory;
import org.jzl.android.recyclerview.v3.model.IDataOwner;
import org.jzl.android.recyclerview.v3.util.Functions;
import org.jzl.lang.fun.Function;
import org.jzl.lang.util.ObjectUtils;

import static org.jzl.android.recyclerview.v3.core.IViewHolderFactory.DEFAULT_EMPTY_LAYOUT_VIEW_HOLDER_FACTORY;

public interface IEmptyLayoutManager<T, VH extends IViewHolder> extends IPlugin<T, VH> {

    int DEFAULT_EMPTY_LAYOUT_ITEM_VIEW_TYPE = -1;
    IDataBinder<Object, DefaultViewHolderFactory.DefaultViewHolder> DEFAULT_DATA_BINDER = (context, viewHolder, data) -> {
    };
    Object DEFAULT_EMPTY_LAYOUT_DATA = new Object();

    void updateEmptyLayoutData(T emptyLayoutData);

    boolean isEmptyLayout();

    @NonNull
    static <T, VH extends IViewHolder, T2, VH2 extends VH> IEmptyLayoutManager<T, VH> of(int emptyLayoutItemViewType, T emptyLayoutData, @NonNull IModule<T2, VH2> module, @NonNull Function<T, T2> mapper) {
        return new EmptyLayoutManager<>(emptyLayoutItemViewType, emptyLayoutData, module, mapper);
    }

    @NonNull
    static <T, VH extends IViewHolder, VH2 extends VH> IEmptyLayoutManager<T, VH> of(int emptyLayoutItemViewType, T emptyLayoutData, @NonNull IModule<T, VH2> module) {
        return of(emptyLayoutItemViewType, emptyLayoutData, module, Functions.own());
    }

    @NonNull
    static <T, VH extends IViewHolder, T2, VH2 extends VH> IEmptyLayoutManager<T, VH> of(int emptyLayoutItemViewType, @LayoutRes int emptyLayoutResId, T emptyLayoutData, @NonNull IViewHolderFactory<VH2> viewHolderFactory, @NonNull IDataBinder<T2, VH2> dataBinder, @NonNull Function<T, T2> mapper) {
        return of(emptyLayoutItemViewType, emptyLayoutData, new IModule<T2, VH2>() {
            @NonNull
            @Override
            public IOptions<T2, VH2> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<T2> dataGetter) {
                return configuration.options(this, viewHolderFactory, dataGetter)
                        .createItemView(emptyLayoutResId, emptyLayoutItemViewType)
                        .dataBindingByItemViewTypes(dataBinder, emptyLayoutItemViewType)
                        .build();
            }
        }, mapper);
    }

    @NonNull
    static <T, VH extends IViewHolder, VH2 extends VH> IEmptyLayoutManager<T, VH> of(int emptyLayoutItemViewType, @LayoutRes int emptyLayoutResId, T emptyLayoutData, @NonNull IViewHolderFactory<VH2> viewHolderFactory, @NonNull IDataBinder<T, VH2> dataBinder) {
        return of(emptyLayoutItemViewType, emptyLayoutResId, emptyLayoutData, viewHolderFactory, dataBinder, Functions.own());
    }

    @NonNull
    static <T> IEmptyLayoutManager<T, IViewHolder> of(int emptyLayoutItemViewType, @LayoutRes int emptyLayoutResId) {
        return of(emptyLayoutItemViewType, emptyLayoutResId, null, DEFAULT_EMPTY_LAYOUT_VIEW_HOLDER_FACTORY, DEFAULT_DATA_BINDER, target -> {
            if (target instanceof IDataOwner) {
                return ((IDataOwner) target).getData();
            } else {
                return ObjectUtils.get(target, DEFAULT_EMPTY_LAYOUT_DATA);
            }
        });
    }

    @NonNull
    static <T> IEmptyLayoutManager<T, IViewHolder> of(@LayoutRes int emptyLayoutResId) {
        return of(DEFAULT_EMPTY_LAYOUT_ITEM_VIEW_TYPE, emptyLayoutResId);
    }

}
