package org.jzl.android.recyclerview.v3.core;

import org.jzl.lang.util.ArrayUtils;

public interface IMatchPolicy {

    IMatchPolicy MATCH_POLICY_ALL = itemViewType -> true;

    static IMatchPolicy ofItemTypes(int... itemViewTypes) {
        return itemViewType -> ArrayUtils.contains(itemViewTypes, itemViewType);
    }

    boolean match(int itemViewType);
}
