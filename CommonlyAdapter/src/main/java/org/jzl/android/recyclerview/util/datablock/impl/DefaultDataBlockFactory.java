package org.jzl.android.recyclerview.util.datablock.impl;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockFactory;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.PositionType;

public class DefaultDataBlockFactory<T> implements DataBlockFactory<T> {

    @Override
    @NonNull
    public DataBlock<T> createDataBlock(DataBlockProvider<T> dataBlockProvider, PositionType positionType, int dataBlockId) {
        return new DataBlockImpl<>(dataBlockId, positionType);
    }
}
