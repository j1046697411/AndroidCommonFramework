package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import org.jzl.lang.util.ArrayUtils;
import org.jzl.lang.util.CollectionUtils;

import java.util.List;

public interface IBindPolicy {

    IBindPolicy BIND_POLICY_ALL = context -> true;

    IBindPolicy BIND_POLICY_NOT_INCLUDED_PAYLOADS = context -> CollectionUtils.isEmpty(context.getPayloads());

    @NonNull
    static IBindPolicy ofItemViewTypes(int... itemViewTypes) {
        if (ArrayUtils.nonEmpty(itemViewTypes)) {
            return context -> ArrayUtils.contains(itemViewTypes, context.getItemViewType());
        } else {
            return BIND_POLICY_ALL;
        }
    }

    @NonNull
    static IBindPolicy ofPayloads(Object... payloads) {
        if (ArrayUtils.nonEmpty(payloads)) {
            return context -> {
                List<Object> targetPayloads = context.getPayloads();
                if (CollectionUtils.nonEmpty(targetPayloads)) {
                    for (Object payload : payloads) {
                        if (targetPayloads.contains(payload)) {
                            return true;
                        }
                    }
                }
                return false;
            };
        } else {
            return BIND_POLICY_ALL;
        }
    }

    boolean match(@NonNull IContext context);

    @NonNull
    default IBindPolicy or(IBindPolicy bindPolicy) {
        return context -> bindPolicy.match(context) || match(context);
    }

    @NonNull
    default IBindPolicy and(IBindPolicy bindPolicy) {
        return context -> bindPolicy.match(context) && match(context);
    }

    @NonNull
    default IBindPolicy negate() {
        return context -> !match(context);
    }

}
