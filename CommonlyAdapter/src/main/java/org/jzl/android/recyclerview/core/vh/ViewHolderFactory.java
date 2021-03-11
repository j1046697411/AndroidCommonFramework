package org.jzl.android.recyclerview.core.vh;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface ViewHolderFactory<VH extends RecyclerView.ViewHolder> {
    VH createViewHolder(View itemView, int viewType);
}
