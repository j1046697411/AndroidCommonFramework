package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.List;

public class DataBinderStore<T, VH extends IViewHolder> implements IDataBinderStore<T, VH>, IBindPolicy, IDataBinder<T, VH> {

    @NonNull
    private final IOptions<T, VH> options;
    @NonNull
    private final List<IDataBinderOwner<T, VH>> dataBinderOwners;

    public DataBinderStore(@NonNull IOptions<T, VH> options, @NonNull List<IDataBinderOwner<T, VH>> dataBinderOwners) {
        this.options = options;
        this.dataBinderOwners = dataBinderOwners;
    }

    @NonNull
    @Override
    public IBindPolicy getBindPolicy() {
        return this;
    }

    @NonNull
    @Override
    public IDataBinder<T, VH> getDataBinder() {
        return this;
    }

    @Override
    public int getPriority() {
        return options.getPriority();
    }

    @Override
    public IDataBinderOwner<T, VH> get(IContext context) {
        return ForeachUtils.findByOne(this.dataBinderOwners, target -> target.getBindPolicy().match(context));
    }

    @Override
    public boolean match(@NonNull IContext context) {
        return ObjectUtils.nonNull(get(context));
    }

    @Override
    public void binding(IContext context, VH viewHolder, T data) {
        for (IDataBinderOwner<T, VH> dataBinderOwner : this.dataBinderOwners) {
            if (dataBinderOwner.getBindPolicy().match(context)) {
                dataBinderOwner.getDataBinder().binding(context, viewHolder, data);
            }
        }
    }
}
