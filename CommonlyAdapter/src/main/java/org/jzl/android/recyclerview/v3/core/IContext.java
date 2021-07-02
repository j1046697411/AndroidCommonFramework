package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import java.util.List;

public interface IContext {

    @NonNull
    IConfiguration<?, ?> getConfiguration();

    @NonNull
    IOptions<?, ?> getOptions();

    int getItemViewType();

    @NonNull
    List<Object> getPayloads();

}
