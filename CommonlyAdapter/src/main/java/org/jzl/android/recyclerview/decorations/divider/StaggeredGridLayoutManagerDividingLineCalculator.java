package org.jzl.android.recyclerview.decorations.divider;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jzl.lang.util.ObjectUtils;

public class StaggeredGridLayoutManagerDividingLineCalculator implements LayoutManagerDividingLineCalculator<StaggeredGridLayoutManager>{

    @Override
    public void calculationDividingLine(@NonNull RecyclerView recyclerView, @NonNull StaggeredGridLayoutManager layoutManager, @NonNull Rect outDividingLine, @NonNull DividerItemDecoration dividerItemDecoration, @NonNull View view, @NonNull RecyclerView.State state) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int layoutPosition = recyclerView.getChildLayoutPosition(view);
        int offset = dividerItemDecoration.getOffset();
        Rect roundDividingLine = dividerItemDecoration.getDividingLineBounds();
        int spanIndex = layoutParams.getSpanIndex();
        int spanCount = layoutManager.getSpanCount();
        if (layoutManager.canScrollHorizontally()) {
            if (isFirst(layoutPosition, spanIndex, layoutManager)) {
                outDividingLine.left = roundDividingLine.left;
            } else {
                outDividingLine.left = offset;
            }
            if (isLast(layoutPosition, spanIndex, layoutManager)) {
                outDividingLine.right = roundDividingLine.right;
            }
            if (spanIndex == 0) {
                outDividingLine.top = roundDividingLine.top;
            } else {
                outDividingLine.top = offset;
            }
            if (spanIndex == spanCount - 1) {
                outDividingLine.bottom = roundDividingLine.bottom;
            }
        } else {
            if (isFirst(layoutPosition, spanIndex, layoutManager)) {
                outDividingLine.top = roundDividingLine.top;
            } else {
                outDividingLine.top = offset;
            }
            if (isLast(layoutPosition, spanIndex, layoutManager)) {
                outDividingLine.bottom = roundDividingLine.bottom;
            }
            outDividingLine.top = offset;
            if (spanIndex == 0) {
                outDividingLine.left = roundDividingLine.left;
            } else {
                outDividingLine.left = offset;
            }
            if (spanIndex == spanCount - 1) {
                outDividingLine.right = roundDividingLine.right;
            }
        }

    }

    private boolean isFirst(int layoutPosition, int spanIndex, StaggeredGridLayoutManager staggeredGridLayoutManager) {
        if (layoutPosition == 0) {
            return true;
        }
        for (int i = layoutPosition - 1; i >= 0; i--) {
            View frontView = staggeredGridLayoutManager.findViewByPosition(i);
            if (isSpanIndexOrFullSpan(frontView, spanIndex)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLast(int layoutPosition, int spanIndex, StaggeredGridLayoutManager staggeredGridLayoutManager) {
        int itemCount = staggeredGridLayoutManager.getItemCount();
        for (int i = layoutPosition + 1; i < itemCount; i++) {
            View frontView = staggeredGridLayoutManager.findViewByPosition(i);
            if (isSpanIndexOrFullSpan(frontView, spanIndex)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSpanIndexOrFullSpan(View view, int spanIndex) {
        if (ObjectUtils.isNull(view)) {
            return true;
        }
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        return layoutParams.isFullSpan() && layoutParams.getSpanIndex() == spanIndex;
    }
}
