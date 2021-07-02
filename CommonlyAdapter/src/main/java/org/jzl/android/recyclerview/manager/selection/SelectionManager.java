package org.jzl.android.recyclerview.manager.selection;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.Component;
import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.configuration.ConfigurationBuilder;
import org.jzl.android.recyclerview.core.data.DataProvider;
import org.jzl.android.recyclerview.core.data.Selectable;
import org.jzl.android.recyclerview.core.item.ItemDataHolder;
import org.jzl.android.recyclerview.core.observer.AdapterDataObservable;
import org.jzl.android.recyclerview.core.vh.DataBindingMatchPolicy;
import org.jzl.android.recyclerview.manager.AbstractManager;
import org.jzl.lang.util.ArrayUtils;
import org.jzl.lang.util.ObjectUtils;

public class SelectionManager<T extends Selectable, VH extends RecyclerView.ViewHolder> extends AbstractManager<T, VH> implements Component<T, VH>, Selector<T> {

    public static final String PAYLOAD = "selection";

    private final DataBindingMatchPolicy matchPolicy;
    private final SelectionInterceptor<T, VH> interceptor;
    private DataProvider<T, VH> dataProvider;
    private AdapterDataObservable adapterDataObservable;
    private SelectionMode selectionMode;

    public SelectionManager(SelectionInterceptor<T, VH> interceptor, DataBindingMatchPolicy matchPolicy) {
        this(SelectionMode.SINGLE, interceptor, matchPolicy);
    }

    public SelectionManager(SelectionMode selectionMode, SelectionInterceptor<T, VH> interceptor, DataBindingMatchPolicy matchPolicy) {
        this.interceptor = interceptor;
        this.selectionMode = selectionMode;
        this.matchPolicy = matchPolicy;
    }

    public static <T extends Selectable, VH extends RecyclerView.ViewHolder> SelectionManager<T, VH> of(SelectionMode selectionMode, SelectionInterceptor<T, VH> interceptor, DataBindingMatchPolicy matchPolicy) {
        return new SelectionManager<>(selectionMode, interceptor, matchPolicy);
    }

    public static <T extends Selectable, VH extends RecyclerView.ViewHolder> SelectionManager<T, VH> of(SelectionMode selectionMode, SelectionInterceptor<T, VH> interceptor) {
        return of(selectionMode, interceptor, DataBindingMatchPolicy.MATCH_POLICY_ALL);
    }

    public static <T extends Selectable, VH extends RecyclerView.ViewHolder> SelectionManager<T, VH> of(SelectionInterceptor<T, VH> interceptor) {
        return of(SelectionMode.SINGLE, interceptor);
    }

    public static <T extends Selectable, VH extends RecyclerView.ViewHolder> SelectionManager<T, VH> of(SelectionMode selectionMode, @IdRes int... ids) {
        return of(selectionMode, new SelectionManager.DefaultSelectionInterceptor<>(ids));
    }

    public static <T extends Selectable, VH extends RecyclerView.ViewHolder> SelectionManager<T, VH> of(@IdRes int... ids) {
        return of(SelectionMode.SINGLE, ids);
    }

    @Override
    public void setup(ConfigurationBuilder<T, VH> builder) {
        super.setup(builder);
        builder.addOnCreatedViewHolderListener((configuration, dataHolder, holder) -> intercept(dataHolder, holder));
    }

    @Override
    public void initialise(RecyclerView recyclerView, Configuration<T, VH> configuration) {
        this.dataProvider = configuration.getDataProvider();
        this.adapterDataObservable = configuration.getAdapterDataObservable();
    }

    protected void intercept(ItemDataHolder<T, VH> dataHolder, VH viewHolder) {
        if (matchPolicy.match(dataHolder)) {
            interceptor.intercept(dataHolder, viewHolder, this);
        }
    }

    @Override
    public void checked(int position, boolean checked) {
        if (selectionMode == SelectionMode.SINGLE) {
            uncheckedAll(false);
        }
        T data = dataProvider.get(position);
        if (data.isChecked() != checked) {
            data.checked(checked);
            adapterDataObservable.notifyItemChanged(position, PAYLOAD);
        }
    }

    @Override
    public void checked(T data, boolean checked) {
        checked(dataProvider.indexOf(data), checked);
    }

    @Override
    public void checkedAll() {
        if (selectionMode == SelectionMode.SINGLE) {
            return;
        }
        for (int i = 0; i < dataProvider.size(); i++) {
            T data = dataProvider.get(i);
            if (ObjectUtils.nonNull(data)) {
                data.checked(true);
            }
        }
        adapterDataObservable.notifyItemRangeChanged(0, dataProvider.size(), PAYLOAD);
    }

    @Override
    public void uncheckedAll() {
        uncheckedAll(true);
    }

    private void uncheckedAll(boolean notify) {
        for (int i = 0; i < dataProvider.size(); i++) {
            T data = dataProvider.get(i);
            if (ObjectUtils.nonNull(data) && data.isChecked()) {
                data.checked(false);
                adapterDataObservable.notifyItemChanged(i, PAYLOAD);
            }
        }
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        this.selectionMode = ObjectUtils.get(selectionMode, this.selectionMode);
    }

    public static class DefaultSelectionInterceptor<T extends Selectable, VH extends RecyclerView.ViewHolder> implements SelectionInterceptor<T, VH> {

        @IdRes
        private final int[] ids;

        public DefaultSelectionInterceptor(@IdRes int... ids) {
            this.ids = ids;
        }

        @Override
        public void intercept(ItemDataHolder<T, VH> dataHolder, VH viewHolder, Selector<T> selector) {
            if (ArrayUtils.nonEmpty(ids)) {
                bindClicks(viewHolder, ids, v -> intercept(selector, dataHolder.getItemData(), viewHolder.getAdapterPosition()));
            } else {
                dataHolder.getConfiguration().addOnItemClickListener((configuration, viewHolder1, data) -> {
                    intercept(selector, data, viewHolder1.getAdapterPosition());
                }, DataBindingMatchPolicy.MATCH_POLICY_ALL);
            }
        }

        protected void intercept(Selector<T> selector, T data, int position) {
            selector.checked(position, !data.isChecked());
        }

        private void bindClicks(VH holder, int[] ids, View.OnClickListener listener) {
            for (int id : ids) {
                holder.itemView.findViewById(id).setOnClickListener(listener);
            }
        }
    }

}
