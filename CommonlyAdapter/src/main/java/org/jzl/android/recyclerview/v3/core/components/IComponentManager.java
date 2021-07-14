package org.jzl.android.recyclerview.v3.core.components;

import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface IComponentManager<T, VH extends IViewHolder> extends IComponent<T, VH> {

    void addComponent(IComponent<T, VH> component);

}
