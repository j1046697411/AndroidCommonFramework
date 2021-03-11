package org.jzl.android.recyclerview.core.data;

public interface Differentiable<T extends Differentiable<T>> {

    boolean areItemsTheSame(T data);

    boolean areContentsTheSame(T data);

    Object getChangePayload(T data);
}
