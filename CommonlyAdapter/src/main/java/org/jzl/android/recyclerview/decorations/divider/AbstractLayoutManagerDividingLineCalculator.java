package org.jzl.android.recyclerview.decorations.divider;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractLayoutManagerDividingLineCalculator<LM extends RecyclerView.LayoutManager> implements LayoutManagerDividingLineCalculator<LM> {

    @Override
    public void calculationDividingLine(@NonNull RecyclerView recyclerView, @NonNull LM layoutManager, @NonNull Rect outRect, @NonNull DividerItemDecoration dividerItemDecoration, @NonNull View view, @NonNull RecyclerView.State state) {
        if (layoutManager.canScrollHorizontally()) {
            calculationHorizontallyDividingLine(recyclerView, layoutManager, outRect, dividerItemDecoration, view, state);
        } else {
            calculationVerticallyDividingLine(recyclerView, layoutManager, outRect, dividerItemDecoration, view, state);
        }
    }

    protected abstract void calculationHorizontallyDividingLine(@NonNull RecyclerView recyclerView, @NonNull LM layoutManager, @NonNull Rect outRect, @NonNull DividerItemDecoration dividerItemDecoration, @NonNull View view, @NonNull RecyclerView.State state);

    protected abstract void calculationVerticallyDividingLine(@NonNull RecyclerView recyclerView, @NonNull LM layoutManager, @NonNull Rect outRect, @NonNull DividerItemDecoration dividerItemDecoration, @NonNull View view, @NonNull RecyclerView.State state);
}
