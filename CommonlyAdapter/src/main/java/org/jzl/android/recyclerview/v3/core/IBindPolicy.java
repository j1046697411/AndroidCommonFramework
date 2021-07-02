package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import org.jzl.lang.util.ArrayUtils;
import org.jzl.lang.util.CollectionUtils;

import java.util.List;

public interface IBindPolicy {

    IBindPolicy BIND_POLICY_ALL = context -> true;

    @NonNull
    static IBindPolicy ofItemViewTypes(int... itemViewTypes) {
        return context -> ArrayUtils.contains(itemViewTypes, context.getItemViewType());
    }

    @NonNull
    static IBindPolicy ofPayloads(Object... payloads) {
        return context -> {
            List<Object> targetPayloads = context.getPayloads();
            if (CollectionUtils.nonEmpty(targetPayloads) && ArrayUtils.nonEmpty(payloads)) {
                for (Object payload : payloads) {
                    if (targetPayloads.contains(payload)) {
                        return true;
                    }
                }
            }
            return false;
        };
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
