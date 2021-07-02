package org.jzl.android.recyclerview.manager.group;

import android.util.SparseArray;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.configuration.ConfigurationBuilder;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.manager.AbstractManager;
import org.jzl.android.recyclerview.util.datablock.DataBlock;
import org.jzl.android.recyclerview.util.datablock.DataBlockGroup;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.PositionType;
import org.jzl.android.recyclerview.util.datablock.impl.DataBlockGroupImpl;
import org.jzl.android.recyclerview.util.datablock.impl.DefaultDataBlockFactory;
import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

public class DefaultGroupManager<T, VH extends RecyclerView.ViewHolder> extends AbstractManager<T, VH> implements GroupManager<T, VH> {

    private final SparseArray<GroupExpandable<T>> groups = new SparseArray<>();
    private final DataBlockGroupFactory<T> dataBlockGroupFactory = (dataBlockProvider1, positionType, dataBlockId) -> new DataBlockGroupImpl<>(dataBlockId, positionType, DefaultGroupExpandable.CONTENT_DATA_BLOCK_ID, new DefaultDataBlockFactory<>());
    private DataBlockProvider<T> dataBlockProvider;
    private GroupHelper<T, VH> groupHelper;
    private boolean enable = false;

    @Override
    public void setup(ConfigurationBuilder<T, VH> builder) {
        super.setup(builder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        if (isEnable() && ObjectUtils.isNull(groupHelper)) {
            throw new NullPointerException("groupHelper is null");
        }
        DataProvider<T, VH> dataProvider = configuration.getDataProvider();
        if (dataProvider instanceof DataBlockProvider) {
            this.dataBlockProvider = (DataBlockProvider<T>) dataProvider;
        } else {
            throw new RuntimeException("DataProvider type (" + dataProvider.getClass().getSimpleName() + ") not implemented (" + DataBlockProvider.class.getCanonicalName() + ")");
        }
    }

    @Override
    public void setGroupHelper(GroupHelper<T, VH> groupHelper) {
        this.groupHelper = groupHelper;
    }

    @Override
    public void replaceAll(DataBlockProvider<T> dataBlockProvider, List<T> newData) {
        if (ObjectUtils.nonNull(groupHelper)) {
            ForeachUtils.each(newData, target -> groupHelper.group(this, target));
        }
    }

    @Override
    public DataBlockGroupFactory<T> getDataBlockFactory() {
        return dataBlockGroupFactory;
    }

    @Override
    public void enable() {
        this.enable = true;
    }

    @Override
    public void disable() {
        this.enable = false;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public GroupExpandable<T> group(int groupId) {
        GroupExpandable<T> group = findGroupByGroupId(groupId);
        if (ObjectUtils.isNull(group)) {
            group = createGroup(groupId);
            groups.put(groupId, group);
        }
        return group;
    }

    protected GroupExpandable<T> createGroup(int groupId) {
        DataBlockGroup<T> dataBlockGroup = dataBlockGroupFactory.createDataBlock(this.dataBlockProvider, PositionType.CONTENT, groupId);
        this.dataBlockProvider.addDataBlock(dataBlockGroup);
        return new DefaultGroupExpandable<>(groupId, dataBlockGroup);
    }

    @Override
    public GroupExpandable<T> findGroupByGroupId(int groupId) {
        return groups.get(groupId);
    }

    @Override
    public GroupExpandable<T> findGroupByPosition(int position) {
        DataBlock<T> dataBlock = dataBlockProvider.findDataBlockByPosition(position);
        if (ObjectUtils.nonNull(dataBlock)) {
            for (int i = 0, size = groups.size(); i < size; i++) {
                GroupExpandable<T> group = groups.valueAt(i);
                if (group.getGroupDataBlock() == dataBlock) {
                    return group;
                }
            }
        }
        return null;
    }

    @Override
    public void toggleExpand(int groupId) {
        group(groupId).toggleExpand();
    }

    @Override
    public void expand(int groupId) {
        group(groupId).expand();
    }

    @Override
    public void collapsed(int groupId) {
        group(groupId).collapsed();
    }

    @SafeVarargs
    @Override
    public final void addAll(T... data) {
        ForeachUtils.each(data, target -> groupHelper.group(this, target));
    }

    @Override
    public void addAll(Collection<T> collection) {
        ForeachUtils.each(collection, target -> groupHelper.group(this, target));
    }

}
