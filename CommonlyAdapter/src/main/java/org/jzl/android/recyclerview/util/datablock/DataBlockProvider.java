package org.jzl.android.recyclerview.util.datablock;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Set;

public interface DataBlockProvider<T> extends DataSource<T> {

    @NonNull
    Collection<DataBlock<T>> getDataBlocks();

    @NonNull
    DataBlockObserver<T, DataBlockProvider<T>> getDataBlockObserver();

    void addOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, DataBlockProvider<T>> dataBlockChangedCallback);

    void removeOnDataBlockChangedCallback(OnDataBlockChangedCallback<T, DataBlockProvider<T>> dataBlockChangedCallback);

    void addDataBlock(@NonNull DataBlock<T> dataBlock);

    void addDataBlockAll(@NonNull Collection<DataBlock<T>> dataBlocks);

    void removeDataBlock(@NonNull DataBlock<T> dataBlock);

    void removeDataBlockAll(@NonNull Collection<DataBlock<T>> dataBlocks);

    DataBlock<T> dataBlock(PositionType positionType, int dataBlockId);

    DataBlock<T> defaultDataBlock();

    DataBlock<T> lastDataBlock();

    DataBlock<T> lastContentDataBlock();

    Set<DataBlock<T>> findDataBlockByPositionType(PositionType positionType);

    DataBlock<T> findDataBlockByPositionTypeAndDataBlockId(PositionType positionType, int dataBlockId);

    DataBlock<T> findDataBlockByPosition(int position);
}
