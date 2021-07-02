package org.jzl.android.recyclerview.core.item;

import org.jzl.android.recyclerview.core.vh.DataBindingMatchPolicy;
import org.jzl.lang.util.ArrayUtils;

public interface ItemBindingMatchPolicy {

    ItemBindingMatchPolicy MATCH_POLICY_ALL = itemType -> itemType >= 0; //默认匹配正整数的值

    static ItemBindingMatchPolicy of(int... itemTypes) {
        if (ArrayUtils.nonEmpty(itemTypes)) {
            return ArrayUtils.isEmpty(itemTypes) ? MATCH_POLICY_ALL : itemType -> ArrayUtils.contains(itemTypes, itemType);
        } else {
            return MATCH_POLICY_ALL;
        }
    }

    boolean match(int itemType);

    default DataBindingMatchPolicy toDataBindingMatchPolicy() {
        return context -> match(context.getItemViewType());
    }

}
