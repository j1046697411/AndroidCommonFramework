package org.jzl.android.recyclerview.v3.core.components;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface IComponent<T, VH extends IViewHolder> {

    void initialize(IConfiguration<T, VH> configuration);

}
