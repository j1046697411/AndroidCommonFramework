package org.jzl.android.recyclerview.v3.core;

public interface IDataBinderStore<T, VH extends IViewHolder> extends IDataBinderOwner<T, VH> {

    IDataBinderOwner<T, VH> get(IContext context);

}
