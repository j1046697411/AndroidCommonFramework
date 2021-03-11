package org.jzl.android.recyclerview.core.configuration;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.jzl.android.recyclerview.R;

public class ViewPagerConfigurationImpl<T, VH extends RecyclerView.ViewHolder> extends AbstractConfiguration<ViewPager2, T, VH> implements ViewPagerConfiguration<T, VH> {

    public ViewPagerConfigurationImpl(ViewPager2 targetView, ConfigurationBuilderImpl<T, VH> configurationBuilder) {
        super(targetView, configurationBuilder);
    }

    @Override
    protected void initialise(ViewPager2 targetView, RecyclerView.Adapter<VH> adapter) {
        targetView.setAdapter(adapter);
        targetView.setTag(R.id.tag_configuration);
    }


    @Override
    public ViewPager2 getViewPager2() {
        return targetView;
    }
}
