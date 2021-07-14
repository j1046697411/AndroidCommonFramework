package org.jzl.android.recyclerview.v3.core.diff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IItemCallback<T> {

    boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem);

    boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem);

    @Nullable
    Object getChangePayload(@NonNull T oldItem, @NonNull T newItem);
}