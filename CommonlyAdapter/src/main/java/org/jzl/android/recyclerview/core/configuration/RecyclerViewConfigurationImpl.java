package org.jzl.android.recyclerview.core.configuration;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.R;
import org.jzl.lang.util.ObjectUtils;

class RecyclerViewConfigurationImpl<T, VH extends RecyclerView.ViewHolder> extends AbstractConfiguration<RecyclerView, T, VH> implements RecyclerViewConfiguration<T, VH> {

    public RecyclerViewConfigurationImpl(RecyclerView targetView, ConfigurationBuilderImpl<T, VH> configurationBuilder) {
        super(targetView, configurationBuilder);
    }

    @Override
    protected void initialise(RecyclerView targetView, RecyclerView.Adapter<VH> adapter) {
        targetView.setAdapter(adapter);
        targetView.setTag(R.id.tag_configuration, this);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return targetView;
    }


    @Override
    public Activity getCurrentActivity() {
        Activity activity = super.getCurrentActivity();
        if (ObjectUtils.nonNull(activity)){
            return activity;
        }
        Context context = getRecyclerView().getContext();
        if (context instanceof Activity){
            return (Activity) context;
        }
        return null;
    }
}
