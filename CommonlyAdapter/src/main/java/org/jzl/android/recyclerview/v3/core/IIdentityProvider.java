package org.jzl.android.recyclerview.v3.core;

@FunctionalInterface
public
interface IIdentityProvider<T, VH extends IViewHolder> {

    long getItemId(IConfiguration<T, VH> configuration, IDataProvider<T> dataProvider, int position);

}
