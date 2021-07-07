package org.jzl.android.recyclerview.v3.core.vh;

import android.view.View;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;
import org.jzl.lang.util.ForeachUtils;

import java.util.ArrayList;
import java.util.List;

class Observable<VH extends IViewHolder> implements IObservable<VH>, View.OnClickListener, View.OnLongClickListener {

    @NonNull
    private final IViewHolderOwner<VH> owner;
    private final List<IObserver< VH>> observers;

    public Observable(@NonNull IViewHolderOwner<VH> owner) {
        this.owner = owner;
        this.observers = new ArrayList<>();
        owner.getItemView().setOnClickListener(this);
        owner.getItemView().setOnLongClickListener(this);
    }

    @Override
    public void observe(@NonNull IObserver<VH> observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unobserved(@NonNull IObserver<VH> observer) {
        observers.remove(observer);
    }

    @Override
    public void onClick(View v) {
        ForeachUtils.each(this.observers, target -> target.onClickItemView(owner));
    }

    @Override
    public boolean onLongClick(View v) {
        boolean isLongClick = false;
        for (IObserver< VH> observer : observers) {
            if (observer.onLongClickItemView(owner)) {
                isLongClick = true;
            }
        }
        return isLongClick;
    }
}
