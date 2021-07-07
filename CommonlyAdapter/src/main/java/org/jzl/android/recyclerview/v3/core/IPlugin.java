package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IPlugin<T, VH extends IViewHolder> {

    void setup(@NonNull IConfigurationBuilder<T, VH> builder);
}
