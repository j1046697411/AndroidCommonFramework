package org.jzl.android.recyclerview.util.datablock;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.util.datablock.impl.DataBlockProviderImpl;
import org.jzl.android.recyclerview.util.datablock.impl.DefaultDataBlockFactory;

public class DataBlockProviders {

    public static final int DEFAULT_DATA_BLOCK_ID = 1;

    public static <T> DataBlockProvider<T> dataBlockProvider(int defaultDataBlockId, @NonNull DataBlockFactory<T> dataBlockFactory) {
        return new DataBlockProviderImpl<>(defaultDataBlockId, dataBlockFactory);
    }

    public static <T> DataBlockProvider<T> dataBlockProvider(int defaultDataBlockId) {
        return dataBlockProvider(defaultDataBlockId, new DefaultDataBlockFactory<>());
    }

    public static <T> DataBlockProvider<T> dataBlockProvider() {
        return dataBlockProvider(DEFAULT_DATA_BLOCK_ID);
    }

}
