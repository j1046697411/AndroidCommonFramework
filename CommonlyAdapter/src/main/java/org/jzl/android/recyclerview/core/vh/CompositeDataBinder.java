package org.jzl.android.recyclerview.core.vh;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class CompositeDataBinder<T, VH extends RecyclerView.ViewHolder> implements DataBinder<T, VH> {

    private final List<DataBinder<T, VH>> dataBinders;

    CompositeDataBinder(List<DataBinder<T, VH>> dataBinders) {
        this.dataBinders = dataBinders;
    }

    public CompositeDataBinder<T, VH> addDataBinder(DataBinder<T, VH> dataBinder) {
        if (ObjectUtils.nonNull(dataBinder)) {
            dataBinders.add(dataBinder);
        }
        return this;
    }

    public CompositeDataBinder<T, VH> removeDataBinder(DataBinder<T, VH> dataBinder) {
        dataBinders.remove(dataBinder);
        return this;
    }

    @Override
    public void binding(Configuration<T, VH> configuration, VH holder, T data) {
        ForeachUtils.each(dataBinders, target -> target.binding(configuration, holder, data));
    }

    public static <T, VH extends RecyclerView.ViewHolder> CompositeDataBinder<T, VH> of(List<DataBinder<T, VH>> dataBinders) {
        return new CompositeDataBinder<>(dataBinders);
    }

    public static <T, VH extends RecyclerView.ViewHolder> CompositeDataBinder<T, VH> of() {
        return of(new ArrayList<>());
    }

}
