package org.jzl.android.recyclerview.v3.core.layout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

@FunctionalInterface
public interface ILayoutManagerFactory<T, VH extends IViewHolder> {

    @NonNull
    RecyclerView.LayoutManager createLayoutManager(@NonNull IConfiguration<T, VH> configuration, @NonNull RecyclerView recyclerView);

    @NonNull
    static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> linearLayoutManager(int orientation, boolean reverseLayout) {
        return (configuration, recyclerView) -> new LinearLayoutManager(recyclerView.getContext(), orientation, reverseLayout);
    }

    @NonNull
    static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> linearLayoutManager(int orientation) {
        return linearLayoutManager(orientation, false);
    }

    @NonNull
    static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> linearLayoutManager() {
        return linearLayoutManager(LinearLayoutManager.VERTICAL);
    }

    @NonNull
    static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> gridLayoutManager(int spanCount, int orientation, boolean reverseLayout) {
        return (configuration, recyclerView) -> new GridLayoutManager(recyclerView.getContext(), spanCount, orientation, reverseLayout);
    }

    @NonNull
    static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> gridLayoutManager(int spanCount, int orientation) {
        return gridLayoutManager(spanCount, orientation, false);
    }

    @NonNull
    static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> gridLayoutManager(int spanCount) {
        return gridLayoutManager(spanCount, GridLayoutManager.VERTICAL);
    }

    @NonNull
    static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> staggeredGridLayoutManager(int spanCount, int orientation) {
        return (configuration, recyclerView) -> new StaggeredGridLayoutManager(spanCount, orientation);
    }

    @NonNull
    static <T, VH extends IViewHolder> ILayoutManagerFactory<T, VH> staggeredGridLayoutManager(int spanCount) {
        return staggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
    }

}
