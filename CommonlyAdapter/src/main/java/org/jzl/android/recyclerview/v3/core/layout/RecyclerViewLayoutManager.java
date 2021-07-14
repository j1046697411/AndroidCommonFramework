package org.jzl.android.recyclerview.v3.core.layout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.IMatchPolicy;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.components.IComponent;
import org.jzl.android.recyclerview.v3.core.listeners.OnAttachedToRecyclerViewListener;
import org.jzl.android.recyclerview.v3.model.ISpanSizable;
import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RecyclerViewLayoutManager<T, VH extends IViewHolder> implements IComponent<T, VH>,
        OnAttachedToRecyclerViewListener<T, VH>, IRecyclerViewLayoutManager<T, VH> {
    private static final ILayoutManagerFactory<?, ?> LAYOUT_MANAGER_FACTORY = ILayoutManagerFactory.linearLayoutManager();

    private RecyclerView recyclerView;
    private IConfiguration<T, VH> configuration;
    private ILayoutManagerFactory<T, VH> layoutManagerFactory = getDefaultLayoutManagerFactory();
    private ISpanSizeLookup<T, VH> spanSizeLookup;

    private final List<SpanSizeOwner> spanSizeOwners = new ArrayList<>();
    private boolean dirty = false;

    @Override
    public void initialize(IConfiguration<T, VH> configuration) {
        this.configuration = configuration;
        configuration.addAttachedToRecyclerViewListener(this).getComponentManager().addComponent(this);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView, @NonNull IOptions<T, VH> options) {
        this.recyclerView = recyclerView;
        if (ObjectUtils.nonNull(layoutManagerFactory) && ObjectUtils.nonNull(configuration)) {
            bindLayoutManager(recyclerView, configuration, layoutManagerFactory);
        }
    }

    @Override
    public void layoutManager(@NonNull ILayoutManagerFactory<T, VH> layoutManagerFactory) {
        if (ObjectUtils.nonNull(recyclerView) && ObjectUtils.nonNull(configuration)) {
            bindLayoutManager(recyclerView, configuration, layoutManagerFactory);
        } else {
            this.layoutManagerFactory = layoutManagerFactory;
        }

    }

    @Override
    public void setSpanSizeLookup(@NonNull ISpanSizeLookup<T, VH> spanSizeLookup) {
        this.spanSizeLookup = spanSizeLookup;
    }

    @Override
    public void spanSize(@NonNull ISpanSizable spanSizable, @NonNull IMatchPolicy matchPolicy, int priority) {
        this.spanSizeOwners.add(new SpanSizeOwner(spanSizable, matchPolicy, priority));
        dirty = true;
    }

    private void bindLayoutManager(RecyclerView recyclerView, IConfiguration<T, VH> configuration, ILayoutManagerFactory<T, VH> layoutManagerFactory) {
        RecyclerView.LayoutManager layoutManager = layoutManagerFactory.createLayoutManager(configuration, recyclerView);
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return RecyclerViewLayoutManager.this.getSpanSize(configuration, Objects.requireNonNull(recyclerView.getAdapter()), (GridLayoutManager) layoutManager, position);
                }
            });
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    public int getSpanSize(IConfiguration<T, VH> configuration, RecyclerView.Adapter<?> adapter, GridLayoutManager gridLayoutManager, int position) {

        {
            IDataGetter<T> dataGetter = configuration.getDataGetter();
            T data = dataGetter.getData(position);
            if (ObjectUtils.nonNull(data) && data instanceof ISpanSizable) {
                return ((ISpanSizable) data).getSpanSize(gridLayoutManager.getSpanCount());
            }
        }
        {
            sort();
            int itemViewType = adapter.getItemViewType(position);
            SpanSizeOwner spanSizeOwner = ForeachUtils.findByOne(this.spanSizeOwners, target -> target.matchPolicy.match(itemViewType));
            if (ObjectUtils.nonNull(spanSizeOwner)) {
                return spanSizeOwner.spanSize.getSpanSize(gridLayoutManager.getSpanCount());
            }
        }
        {
            if (ObjectUtils.nonNull(spanSizeLookup)) {
                return spanSizeLookup.getSpanSize(configuration, gridLayoutManager.getSpanCount(), position);
            }
        }
        return 1;
    }

    private void sort() {
        if (dirty) {
            Collections.sort(this.spanSizeOwners, Collections.reverseOrder());
            dirty = false;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> getDefaultLayoutManagerFactory() {
        return (ILayoutManagerFactory<T, VH>) LAYOUT_MANAGER_FACTORY;
    }

    static class SpanSizeOwner implements Comparable<SpanSizeOwner> {
        private final ISpanSizable spanSize;
        private final IMatchPolicy matchPolicy;
        private final int priority;

        public SpanSizeOwner(ISpanSizable spanSize, IMatchPolicy matchPolicy, int priority) {
            this.spanSize = spanSize;
            this.matchPolicy = matchPolicy;
            this.priority = priority;
        }

        public ISpanSizable getSpanSize() {
            return spanSize;
        }

        public IMatchPolicy getMatchPolicy() {
            return matchPolicy;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public int compareTo(SpanSizeOwner o) {
            return Integer.compare(priority, o.priority);
        }
    }

}
