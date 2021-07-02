package org.jzl.android.recyclerview.v3.core;

@FunctionalInterface
public
interface IDataClassifier<T, VH extends IViewHolder> {

    int getItemViewType(IConfiguration<T, VH> configuration, IDataProvider<T> dataProvider, int position);

}
