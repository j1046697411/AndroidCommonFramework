package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.model.IExtractable;

import java.util.List;

public interface IContext extends IExtractable {

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
