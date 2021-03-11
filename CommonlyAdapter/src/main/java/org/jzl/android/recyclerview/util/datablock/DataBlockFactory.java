package org.jzl.android.recyclerview.util.datablock;

import androidx.annotation.NonNull;

public interface DataBlockFactory<T> {

    @NonNull
    DataBlock<T> createDataBlock(DataBlockProvider<T> dataBlockProvider, PositionType positionType, int dataBlockId);

}
