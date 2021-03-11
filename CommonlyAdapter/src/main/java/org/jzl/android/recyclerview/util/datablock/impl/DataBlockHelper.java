package org.jzl.android.recyclerview.util.datablock.impl;

import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;

class DataBlockHelper {

    public static <T> int getStartPosition(DataBlockProvider<T> dataBlockProvider, DataBlock<T> dataBlock) {
        int startPosition = 0;
        for (DataBlock<T> db : dataBlockProvider.getDataBlocks()) {
            if (db == dataBlock) {
                return startPosition;
            }
            startPosition += db.size();
        }
        return 0;
    }

}
