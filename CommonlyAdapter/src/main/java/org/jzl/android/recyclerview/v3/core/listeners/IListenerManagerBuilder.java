package org.jzl.android.recyclerview.v3.core.listeners;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IBindPolicy;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface IListenerManagerBuilder<T, VH extends IViewHolder, B extends IListenerManagerBuilder<T, VH, B>> {

    @NonNull
    B addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    default B addOnCreatedViewHolderListener(@NonNull OnCreatedViewHolderListener<T, VH> createdViewHolderListener, int... itemViewTypes) {
        return addOnCreatedViewHolderListener(createdViewHolderListener, IBindPolicy.ofItemViewTypes(itemViewTypes));
    }

    @NonNull
    B addOnClickItemViewListener(@NonNull OnClickItemViewListener<T, VH> clickItemViewListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    default B addOnClickItemViewListener(@NonNull OnClickItemViewListener<T, VH> clickItemViewListener, @NonNull int... itemViewTypes) {
        return addOnClickItemViewListener(clickItemViewListener, IBindPolicy.ofItemViewTypes(itemViewTypes));
    }

    @NonNull
    B addOnLongClickItemViewListener(@NonNull OnLongClickItemViewListener<T, VH> longClickItemViewListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    default B addOnLongClickItemViewListener(@NonNull OnLongClickItemViewListener<T, VH> longClickItemViewListener, int... itemViewTypes) {
        return addOnLongClickItemViewListener(longClickItemViewListener, IBindPolicy.ofItemViewTypes(itemViewTypes));
    }

    @NonNull
    B addViewAttachedToWindowListener(@NonNull OnViewAttachedToWindowListener<T, VH> viewAttachedToWindowListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    B addViewDetachedFromWindowListener(@NonNull OnViewDetachedFromWindowListener<T, VH> viewDetachedFromWindowListener, @NonNull IBindPolicy bindPolicy);

    @NonNull
    B addAttachedToRecyclerViewListener(@NonNull OnAttachedToRecyclerViewListener<T, VH> attachedToRecyclerViewListener);

    @NonNull
    B addDetachedFromRecyclerViewListener(@NonNull OnDetachedFromRecyclerViewListener<T, VH> detachedFromRecyclerViewListener);

    B addViewRecycledListener(@NonNull OnViewRecycledListener<T, VH> viewRecycledListener, @NonNull IBindPolicy bindPolicy);
}
