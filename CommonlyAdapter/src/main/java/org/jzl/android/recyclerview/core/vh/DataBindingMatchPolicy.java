package org.jzl.android.recyclerview.core.vh;

import org.jzl.android.recyclerview.core.item.ItemContext;
import org.jzl.lang.util.ArrayUtils;
import org.jzl.lang.util.CollectionUtils;

import java.util.List;

public interface DataBindingMatchPolicy {

    DataBindingMatchPolicy MATCH_POLICY_ALL_NOT = context -> false;
    DataBindingMatchPolicy MATCH_POLICY_ALL = context -> context.getItemViewType() >= 0;
    DataBindingMatchPolicy MATCH_POLICY_NOT_INCLUDED_PAYLOADS = context -> CollectionUtils.isEmpty(context.getPayloads());

    boolean match(ItemContext context);

    default DataBindingMatchPolicy and(DataBindingMatchPolicy matchPolicy) {
        return context -> match(context) && matchPolicy.match(context);
    }

    default DataBindingMatchPolicy or(DataBindingMatchPolicy matchPolicy) {
        return context -> match(context) || matchPolicy.match(context);
    }

    static DataBindingMatchPolicy ofItemTypes(int... itemTypes) {
        if (ArrayUtils.nonEmpty(itemTypes)) {
            return context -> {
                for (int itemType : itemTypes) {
                    if (itemType == context.getItemViewType()) {
                        return true;
                    }
                }
                return false;
            };
        }
        return MATCH_POLICY_ALL;
    }

    static DataBindingMatchPolicy ofPayloads(Object... payloads) {
        if (ArrayUtils.nonEmpty(payloads)) {
            return context -> {
                List<Object> objects = context.getPayloads();
                if (CollectionUtils.nonEmpty(objects)) {
                    for (Object o : objects) {
                        if (ArrayUtils.contains(payloads, o)) {
                            return true;
                        }
                    }
                }
                return false;
            };
        } else {
            return MATCH_POLICY_ALL;
        }
    }

}
