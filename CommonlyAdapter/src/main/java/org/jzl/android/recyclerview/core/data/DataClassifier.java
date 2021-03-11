package org.jzl.android.recyclerview.core.data;


import androidx.recyclerview.widget.RecyclerView;

public interface DataClassifier<T, VH extends RecyclerView.ViewHolder> {

    int getDataType(DataProvider<T, VH> dataProvider, int position);

}
