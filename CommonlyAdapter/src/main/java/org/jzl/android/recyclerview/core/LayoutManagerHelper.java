package org.jzl.android.recyclerview.core;

import android.util.SparseIntArray;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.configuration.ConfigurationBuilder;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.core.data.SpanSizable;
import org.jzl.android.recyclerview.core.factory.LayoutManagerFactory;
import org.jzl.android.recyclerview.manager.AbstractManager;
import org.jzl.android.recyclerview.manager.layout.EmptyLayoutManager;
import org.jzl.android.recyclerview.util.Logger;
import org.jzl.lang.util.ObjectUtils;

public final class LayoutManagerHelper<T, VH extends RecyclerView.ViewHolder> extends AbstractManager<T, VH> {
    private static final Logger log = Logger.logger(LayoutManagerHelper.class);
    private final SparseIntArray spanSizes = new SparseIntArray();
    private LayoutManagerFactory layoutManagerFactory;
    private RecyclerView recyclerView;
    private Configuration<T, VH> configuration;
    private SpanSizeLookup<T> spanSizeLookup;
    private EmptyLayoutManager<T, VH> emptyLayoutManager;

    private LayoutManagerHelper() {
    }

    public static <T, VH extends RecyclerView.ViewHolder> LayoutManagerHelper<T, VH> of() {
        return new LayoutManagerHelper<>();
    }

    @Override
    public void setup(ConfigurationBuilder<T, VH> builder) {
        super.setup(builder);
        builder.setLayoutManagerFactory(layoutManagerFactory);
    }

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        this.recyclerView = recyclerView;
        this.configuration = configuration;
        this.emptyLayoutManager = configuration.getEmptyLayoutManager();
        bindLayoutManager(recyclerView, configuration.getDataProvider(), configuration.getLayoutManagerFactory());
    }

    private void bindLayoutManager(RecyclerView recyclerView, DataProvider<T, VH> dataProvider, LayoutManagerFactory layoutManagerFactory) {
        RecyclerView.LayoutManager layoutManager = layoutManagerFactory.createLayoutManager(recyclerView.getContext(), recyclerView);
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (dataProvider.isEmpty() && emptyLayoutManager.isEnable()) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (position >= dataProvider.size()) {
                        return spanSizes.get(position, 1);
                    }
                    int spanSize = 1;
                    T data = dataProvider.get(position);
                    if (data instanceof SpanSizable) {
                        spanSize = ((SpanSizable) data).getSpanSize(gridLayoutManager.getSpanCount());
                    } else if (ObjectUtils.nonNull(spanSizeLookup)) {
                        spanSize = spanSizeLookup.getSpanSize(data, gridLayoutManager.getSpanCount(), position);
                    }
                    spanSizes.put(position, spanSize);
                    return spanSize;
                }
            });
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    public LayoutManagerHelper<T, VH> layoutManager(LayoutManagerFactory layoutManagerFactory) {
        if (ObjectUtils.nonNull(recyclerView) && ObjectUtils.nonNull(configuration)) {
            bindLayoutManager(recyclerView, configuration.getDataProvider(), layoutManagerFactory);
        } else {
            this.layoutManagerFactory = layoutManagerFactory;
        }
        return this;
    }

    public LayoutManagerHelper<T, VH> linearLayoutManager(int orientation, boolean reverseLayout) {
        return layoutManager((context, recyclerView) -> new LinearLayoutManager(context, orientation, reverseLayout));
    }

    public LayoutManagerHelper<T, VH> linearLayoutManager(int orientation) {
        return linearLayoutManager(orientation, false);
    }

    public LayoutManagerHelper<T, VH> linearLayoutManager() {
        return linearLayoutManager(LinearLayoutManager.VERTICAL);
    }

    public LayoutManagerHelper<T, VH> gridLayoutManager(int spanCount, int orientation, boolean reverseLayout) {
        return layoutManager((context, recyclerView1) -> new GridLayoutManager(context, spanCount, orientation, reverseLayout));
    }

    public LayoutManagerHelper<T, VH> gridLayoutManager(int spanCount, int orientation) {
        return gridLayoutManager(spanCount, orientation, false);
    }

    public LayoutManagerHelper<T, VH> gridLayoutManager(int spanCount) {
        return gridLayoutManager(spanCount, GridLayoutManager.VERTICAL, false);
    }

    public LayoutManagerHelper<T, VH> staggeredGridLayoutManager(int spanCount, int orientation) {
        return layoutManager((context, recyclerView1) -> new StaggeredGridLayoutManager(spanCount, orientation));
    }

    public LayoutManagerHelper<T, VH> staggeredGridLayoutManager(int spanCount) {
        return staggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
    }

    public LayoutManagerHelper<T, VH> setSpanSizeLookup(SpanSizeLookup<T> spanSizeLookup) {
        this.spanSizeLookup = spanSizeLookup;
        return this;
    }

}
