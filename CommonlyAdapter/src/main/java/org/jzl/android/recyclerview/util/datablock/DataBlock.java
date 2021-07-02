package org.jzl.android.recyclerview.util.datablock;

public interface DataBlock<T> extends DataSource<T> {

    int getSortOrder();

    void setSortOrder(int sortOrder);

    int getDataBlockId();

    PositionType getPositionType();

    int getStartPosition();

    void bindDataBlockProvider(DataBlockProvider<T> dataBlockProvider);

    void unbindDataBlockProvider();
}
