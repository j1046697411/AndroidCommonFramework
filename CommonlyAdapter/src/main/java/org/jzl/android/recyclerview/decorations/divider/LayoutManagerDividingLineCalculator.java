package org.jzl.android.recyclerview.decorations.divider;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface LayoutManagerDividingLineCalculator<LM extends RecyclerView.LayoutManager> {

    void calculationDividingLine(@NonNull RecyclerView recyclerView, @NonNull LM layoutManager, @NonNull Rect outRect, @NonNull DividerItemDecoration dividerItemDecoration, @NonNull View view, @NonNull RecyclerView.State state);

}
