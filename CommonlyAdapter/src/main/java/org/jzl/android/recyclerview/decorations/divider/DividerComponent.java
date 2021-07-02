package org.jzl.android.recyclerview.decorations.divider;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.configuration.Configuration;

public class DividerComponent<T, VH extends RecyclerView.ViewHolder> implements Component<T, VH> {

    private final DividerItemDecoration dividerItemDecoration;

    DividerComponent(DividerItemDecoration dividerItemDecoration) {
        this.dividerItemDecoration = dividerItemDecoration;
    }

    public static <T, VH extends RecyclerView.ViewHolder> DividerComponent<T, VH> of(int left, int top, int right, int bottom, int offset) {
        return new DividerComponent<>(new DividerItemDecoration(left, top, right, bottom, offset));
    }

    public static <T, VH extends RecyclerView.ViewHolder> DividerComponent<T, VH> of(int offset) {
        return of(offset, offset, offset, offset, offset);
    }

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}
