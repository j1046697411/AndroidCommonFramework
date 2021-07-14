package org.jzl.android.recyclerview.v3.core;

import android.view.View;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.model.IExtractable;

import java.util.List;

public interface IContext extends IExtractable {

    @NonNull
    View getItemView();

    @NonNull
    IConfiguration<?, ?> getConfiguration();

    @NonNull
    IOptions<?, ?> getOptions();

    @NonNull
    List<Object> getPayloads();

    int getItemViewType();

    int getLayoutPosition();

    int getAdapterPosition();

    long getItemId();
}
