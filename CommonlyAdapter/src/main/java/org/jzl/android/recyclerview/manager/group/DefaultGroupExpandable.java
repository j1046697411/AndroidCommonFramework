package org.jzl.android.recyclerview.manager.group;

import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.PositionType;
import org.jzl.lang.util.ObjectUtils;

public class DefaultGroupExpandable<T> implements GroupExpandable<T> {

    static final int CONTENT_DATA_BLOCK_ID = 1;
    static final int HEADER_DATA_BLOCK_ID = 2;
    static final int FOOTER_DATA_BLOCK_ID = 3;

    private final int groupId;

    private final DataBlockProvider<T> dataBlockProvider;
    private boolean expand = true;

    private DataBlock<T> contentDataBlock;
    private DataBlock<T> headerDataBlock;
    private DataBlock<T> footerDataBlock;

    DefaultGroupExpandable(int groupId, DataBlockProvider<T> dataBlockProvider) {
        this.dataBlockProvider = dataBlockProvider;
        this.groupId = groupId;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    @Override
    public boolean isExpand() {
        return expand;
    }

    @Override
    public void toggleExpand() {
        if (isExpand()) {
            collapsed();
        } else {
            expand();
        }
    }

    @Override
    public void expand() {
        if (!isExpand()) {
            this.dataBlockProvider.addDataBlock(getGroupContent());
            this.expand = true;
        }
    }

    @Override
    public void collapsed() {
        if (isExpand()) {
            this.dataBlockProvider.removeDataBlock(getGroupContent());
            this.expand = false;
        }
    }

    @Override
    public DataBlockProvider<T> getGroupDataBlock() {
        return dataBlockProvider;
    }

    @Override
    public DataBlock<T> getGroupContent() {
        if (ObjectUtils.isNull(contentDataBlock)) {
            contentDataBlock = dataBlockProvider.defaultDataBlock();
        }
        return contentDataBlock;
    }

    @Override
    public DataBlock<T> getGroupHeader() {
        if (ObjectUtils.isNull(headerDataBlock)) {
            headerDataBlock = dataBlockProvider.dataBlock(PositionType.HEADER, HEADER_DATA_BLOCK_ID);
        }
        return headerDataBlock;
    }

    @Override
    public DataBlock<T> getGroupGroupFooter() {
        if (ObjectUtils.isNull(footerDataBlock)) {
            footerDataBlock = dataBlockProvider.dataBlock(PositionType.FOOTER, FOOTER_DATA_BLOCK_ID);
        }
        return footerDataBlock;
    }
}
