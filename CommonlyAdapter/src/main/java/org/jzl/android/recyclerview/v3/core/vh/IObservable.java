package org.jzl.android.recyclerview.v3.core.vh;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IViewHolder;

public interface IObservable<VH extends IViewHolder> {

    void observe(@NonNull IObserver<VH> observer);

    void unobserved(@NonNull IObserver<VH> observer);
}
