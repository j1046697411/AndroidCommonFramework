package org.jzl.android.recyclerview.v3.core;

import android.view.View;

import androidx.annotation.NonNull;

public interface IViewHolderFactory<VH extends IViewHolder> {

    @NonNull
    VH createViewHolder(@NonNull IOptions<?, VH> options, @NonNull View itemView, int itemViewType);

}
