package org.jzl.android.recyclerview.core.vh;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.ViewBinder;
import org.jzl.android.ViewFinder;

public class CommonlyViewHolder extends RecyclerView.ViewHolder implements ViewFinder, IViewHolder {

    private final ViewBinder viewBinder;

    public CommonlyViewHolder(@NonNull View itemView) {
        super(itemView);
        this.viewBinder = ViewBinder.bind(this);
    }

    @Override
    public <V extends View> V findViewById(int id) {
        return itemView.findViewById(id);
    }

    public ViewBinder getViewBinder() {
        return viewBinder;
    }

    @Override
    public void onDispose() {
    }
}
