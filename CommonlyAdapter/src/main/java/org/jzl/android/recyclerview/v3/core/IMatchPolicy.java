package org.jzl.android.recyclerview.v3.core;

import org.jzl.lang.util.ArrayUtils;

public interface IMatchPolicy {

    IMatchPolicy MATCH_POLICY_ALL = itemViewType -> true;

    static IMatchPolicy ofItemTypes(int... itemViewTypes) {
        if (ArrayUtils.nonEmpty(itemViewTypes)) {
            return itemViewType -> ArrayUtils.contains(itemViewTypes, itemViewType);
        } else {
            return MATCH_POLICY_ALL;
        }
    }

    boolean match(int itemViewType);
}
