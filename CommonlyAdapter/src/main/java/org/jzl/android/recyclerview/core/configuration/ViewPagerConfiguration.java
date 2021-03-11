package org.jzl.android.recyclerview.core.configuration;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public interface ViewPagerConfiguration<T, VH extends RecyclerView.ViewHolder> extends Configuration<T, VH> {

    ViewPager2 getViewPager2();

}
