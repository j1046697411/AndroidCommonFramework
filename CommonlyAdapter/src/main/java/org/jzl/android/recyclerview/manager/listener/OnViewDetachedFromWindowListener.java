package org.jzl.android.recyclerview.manager.listener;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;

public interface OnViewDetachedFromWindowListener<T, VH extends RecyclerView.ViewHolder> {

    void onViewDetachedFromWindow(Configuration<T, VH> configuration, VH holder);

}
