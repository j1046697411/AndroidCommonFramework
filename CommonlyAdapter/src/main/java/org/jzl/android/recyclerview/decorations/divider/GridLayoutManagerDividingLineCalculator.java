package org.jzl.android.recyclerview.decorations.divider;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.util.Logger;
import org.jzl.lang.util.ObjectUtils;

public class GridLayoutManagerDividingLineCalculator implements LayoutManagerDividingLineCalculator<GridLayoutManager> {
    private static final Logger log = Logger.logger(GridLayoutManagerDividingLineCalculator.class);

    @Override
    public void calculationDividingLine(@NonNull RecyclerView recyclerView, @NonNull GridLayoutManager layoutManager, @NonNull Rect outRect, @NonNull DividerItemDecoration dividerItemDecoration, @NonNull View view, @NonNull RecyclerView.State state) {

        Rect roundDividingLine = dividerItemDecoration.getDividingLineBounds();
        int offset = dividerItemDecoration.getOffset();
        int spanCount = layoutManager.getSpanCount();

        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = layoutParams.getSpanIndex();
        int spanSize = layoutParams.getSpanSize();
        int layoutPosition = layoutParams.getViewLayoutPosition();
        log.i("layoutPosition -> " + layoutPosition + "," + spanCount + "," + layoutManager.getItemCount());
        int groupIndex;
        int groupCount;
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        if (ObjectUtils.nonNull(spanSizeLookup)) {
            groupIndex = spanSizeLookup.getSpanGroupIndex(layoutPosition, spanCount);
            groupCount = spanSizeLookup.getSpanGroupIndex(layoutManager.getItemCount() - 1, spanCount);
        } else {
            groupIndex = layoutPosition / spanCount;
            groupCount = (layoutManager.getItemCount() - 1) / spanCount;
        }
        if (layoutManager.canScrollHorizontally()) {
            if (spanIndex == 0) {
                outRect.top = roundDividingLine.top;
            } else {
                outRect.top = offset / 2;
            }
            if (spanIndex + spanSize >= spanCount) {
                outRect.bottom = roundDividingLine.bottom;
            } else {
                outRect.bottom = offset / 2;
            }
            if (groupIndex == 0) {
                outRect.left = roundDividingLine.left;
            } else {
                outRect.left = offset / 2;
            }
            if (groupIndex == groupCount) {
                outRect.right = roundDividingLine.right;
            } else {
                outRect.right = offset / 2;
            }

        } else {
            if (spanIndex == 0) {
                outRect.left = roundDividingLine.left;
            } else {
                outRect.left = offset / 2;
            }
            if (spanIndex + spanSize >= spanCount) {
                outRect.right = roundDividingLine.right;
            } else {
                outRect.right = offset / 2;
            }
            if (groupIndex == 0) {
                outRect.top = roundDividingLine.top;
            } else {
                outRect.top = offset / 2;
            }
            if (groupIndex == groupCount) {
                outRect.bottom = roundDividingLine.bottom;
            } else {
                outRect.bottom = offset / 2;
            }
        }
        Log.i("test", outRect + "," + roundDividingLine + "," + offset);
    }
}
