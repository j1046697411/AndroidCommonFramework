package org.jzl.android.recyclerview.manager.group;

import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;

public interface GroupExpandable<T> {

    int getGroupId();

    boolean isExpand();

    void toggleExpand();

    void expand();

    void collapsed();

    DataBlockProvider<T> getGroupDataBlock();

    DataBlock<T> getGroupContent();

    DataBlock<T> getGroupHeader();

    DataBlock<T> getGroupGroupFooter();
}
