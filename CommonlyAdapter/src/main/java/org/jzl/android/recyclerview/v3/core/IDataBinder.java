package org.jzl.android.recyclerview.v3.core;

public interface IDataBinder<T, VH extends IViewHolder> {

    void binding(IContext context, VH viewHolder, T data);

}
