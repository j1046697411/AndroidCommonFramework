package org.jzl.android.recyclerview.v3.core.plugins;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.IViewHolderOwner;
import org.jzl.android.recyclerview.v3.model.ISelectable;

public interface ISelectInterceptor<T extends ISelectable, VH extends IViewHolder> {

    void intercept(IOptions<T, VH> options, @NonNull IViewHolderOwner<VH> viewHolderOwner, ISelector<T> selector);

}