package org.jzl.android.recyclerview.manager.group;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.manager.Manager;
import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;

import java.util.Collection;
import java.util.List;

public interface GroupManager<T, VH extends RecyclerView.ViewHolder> extends Manager<T, VH> {

    void setGroupHelper(GroupHelper<T, VH> groupHelper);

    void replaceAll(DataBlockProvider<T> dataBlockProvider, List<T> newData);

    DataBlockGroupFactory<T> getDataBlockFactory();

    void enable();

    void disable();

    boolean isEnable();

    GroupExpandable<T> group(int groupId);

    GroupExpandable<T> findGroupByGroupId(int groupId);

    GroupExpandable<T> findGroupByPosition(int position);

    void toggleExpand(int groupId);

    void expand(int groupId);

    void collapsed(int groupId);

    void addAll(T... data);

    void addAll(Collection<T> collection);

    interface GroupHelper<T, VH extends RecyclerView.ViewHolder> {

        void group(GroupManager<T, VH> groupManager, T data);

    }

}
