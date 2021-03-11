package org.jzl.android.recyclerview.decorations.divider;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearLayoutManagerDividingLineCalculator extends AbstractLayoutManagerDividingLineCalculator<LinearLayoutManager> {

    @Override
    protected void calculationHorizontallyDividingLine(@NonNull RecyclerView recyclerView, @NonNull LinearLayoutManager layoutManager, @NonNull Rect outRect, @NonNull DividerItemDecoration dividerItemDecoration, @NonNull View view, @NonNull RecyclerView.State state) {
        int layoutPosition = recyclerView.getChildLayoutPosition(view);
        int itemCount = layoutManager.getItemCount();
        Rect dividingLineBounds = dividerItemDecoration.getDividingLineBounds();
        int offset = dividerItemDecoration.getOffset();
        if (layoutPosition == 0) {
            outRect.left = dividingLineBounds.left;
        }
        if (layoutPosition == itemCount - 1) {
            outRect.right = dividingLineBounds.right;
        } else {
            outRect.right = offset;
        }
        outRect.top = dividingLineBounds.top;
        outRect.bottom = dividingLineBounds.bottom;
    }

    @Override
    protected void calculationVerticallyDividingLine(@NonNull RecyclerView recyclerView, @NonNull LinearLayoutManager layoutManager, @NonNull Rect outRect, @NonNull DividerItemDecoration dividerItemDecoration, @NonNull View view, @NonNull RecyclerView.State state) {
        int layoutPosition = recyclerView.getChildLayoutPosition(view);
        int itemCount = layoutManager.getItemCount();
        Rect dividingLineBounds = dividerItemDecoration.getDividingLineBounds();
        int offset = dividerItemDecoration.getOffset();
        if (layoutPosition == 0) {
            outRect.top = dividingLineBounds.top;
        }
        if (layoutPosition == itemCount - 1) {
            outRect.bottom = dividingLineBounds.bottom;
        } else {
            outRect.bottom = offset;
        }
        outRect.left = dividingLineBounds.left;
        outRect.right = dividingLineBounds.right;
    }
}
