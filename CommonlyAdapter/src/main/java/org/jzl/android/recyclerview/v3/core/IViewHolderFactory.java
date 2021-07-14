package org.jzl.android.recyclerview.v3.core;

import android.view.View;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.vh.DefaultViewHolderFactory;

public interface IViewHolderFactory<VH extends IViewHolder> {

    DefaultViewHolderFactory DEFAULT_EMPTY_LAYOUT_VIEW_HOLDER_FACTORY = new DefaultViewHolderFactory();

    @NonNull
    VH createViewHolder(@NonNull IOptions<?, VH> options, @NonNull View itemView, int itemViewType);

    static IViewHolderFactory<DefaultViewHolderFactory.DefaultViewHolder> of(){
        return DEFAULT_EMPTY_LAYOUT_VIEW_HOLDER_FACTORY;
    }

}
