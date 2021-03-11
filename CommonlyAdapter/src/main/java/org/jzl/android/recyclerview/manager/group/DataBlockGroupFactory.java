package org.jzl.android.recyclerview.manager.group;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.util.datablock.DataBlockFactory;
import org.jzl.android.recyclerview.util.datablock.DataBlockGroup;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.PositionType;

public interface DataBlockGroupFactory<T> extends DataBlockFactory<T> {

    @Override
    @NonNull
    DataBlockGroup<T> createDataBlock(DataBlockProvider<T> dataBlockProvider, PositionType positionType, int dataBlockId);
    
}
