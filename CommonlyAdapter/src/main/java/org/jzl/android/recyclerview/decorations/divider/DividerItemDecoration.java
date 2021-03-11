package org.jzl.android.recyclerview.decorations.divider;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jzl.lang.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int offset;
    private final Rect dividingLineBounds = new Rect();

    private final Map<Class<? extends RecyclerView.LayoutManager>, LayoutManagerDividingLineCalculator<? extends RecyclerView.LayoutManager>> layoutManagerDividingLineCalculators = new HashMap<>();

    public DividerItemDecoration(int left, int top, int right, int bottom, int offset) {
        this.dividingLineBounds.set(left, top, right, bottom);
        this.offset = offset;
        initCalculator();
    }

    private void initCalculator() {
        addLayoutManagerDividingLineCalculator(LinearLayoutManager.class, new LinearLayoutManagerDividingLineCalculator());
        addLayoutManagerDividingLineCalculator(GridLayoutManager.class, new GridLayoutManagerDividingLineCalculator());
        addLayoutManagerDividingLineCalculator(StaggeredGridLayoutManager.class, new StaggeredGridLayoutManagerDividingLineCalculator());
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        LayoutManagerDividingLineCalculator<RecyclerView.LayoutManager> calculator = findLayoutManagerDividingLineCalculator(layoutManager);
        if (ObjectUtils.nonNull(calculator) && ObjectUtils.nonNull(layoutManager)) {
            calculator.calculationDividingLine(parent, layoutManager, outRect, this, view, state);
        } else {
            outRect.set(offset, offset, offset, offset);
        }
    }

    public <LM extends RecyclerView.LayoutManager> void addLayoutManagerDividingLineCalculator(Class<LM> type, LayoutManagerDividingLineCalculator<LM> layoutManagerDividingLineCalculator) {
        if (ObjectUtils.nonNull(type) && ObjectUtils.nonNull(layoutManagerDividingLineCalculator)) {
            this.layoutManagerDividingLineCalculators.put(type, layoutManagerDividingLineCalculator);
        }
    }

    @SuppressWarnings("unchecked")
    private <LM extends RecyclerView.LayoutManager> LayoutManagerDividingLineCalculator<LM> findLayoutManagerDividingLineCalculator(RecyclerView.LayoutManager layoutManager) {
        if (ObjectUtils.nonNull(layoutManager)) {
            return (LayoutManagerDividingLineCalculator<LM>) layoutManagerDividingLineCalculators.get(layoutManager.getClass());
        }
        return null;
    }

    public int getOffset() {
        return offset;
    }

    public Rect getDividingLineBounds() {
        return dividingLineBounds;
    }

}
