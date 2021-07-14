package org.jzl.android.recyclerview.core.factory;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface LayoutManagerFactory {

    RecyclerView.LayoutManager createLayoutManager(Context context, RecyclerView recyclerView);



}