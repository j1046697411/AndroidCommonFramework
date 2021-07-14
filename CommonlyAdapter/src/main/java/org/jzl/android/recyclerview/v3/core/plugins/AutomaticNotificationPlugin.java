package org.jzl.android.recyclerview.v3.core.plugins;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.util.datablock.DataBlockProvider;
import org.jzl.android.recyclerview.util.datablock.OnDataBlockChangedCallback;
import org.jzl.android.recyclerview.v3.core.IAdapterObservable;
import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IConfigurationBuilder;
import org.jzl.android.recyclerview.v3.core.IPlugin;
import org.jzl.android.recyclerview.v3.core.IViewHolder;
import org.jzl.android.recyclerview.v3.core.components.IComponent;

import java.util.List;

public class AutomaticNotificationPlugin<T, VH extends IViewHolder> implements IPlugin<T, VH>, IComponent<T, VH> {
    private static final AutomaticNotificationPlugin<Object, IViewHolder> AUTOMATIC_NOTIFICATION_PLUGIN = new AutomaticNotificationPlugin<>();

    private AutomaticNotificationPlugin() {
    }

    @Override
    public void setup(@NonNull IConfigurationBuilder<T, VH> builder) {
        builder.addComponent(this);
    }

    @Override
    public void initialize(IConfiguration<T, VH> configuration) {
        List<T> dataProvider = configuration.getDataProvider();
        if (dataProvider instanceof DataBlockProvider) {
            ((DataBlockProvider<T>) dataProvider).addOnDataBlockChangedCallback(new DataBlockChangedCallback(configuration.getAdapterObservable()));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, VH extends IViewHolder> AutomaticNotificationPlugin<T, VH> of(){
        return (AutomaticNotificationPlugin<T, VH>) AUTOMATIC_NOTIFICATION_PLUGIN;
    }

    class DataBlockChangedCallback implements OnDataBlockChangedCallback<T, DataBlockProvider<T>> {

        private final IAdapterObservable<T, VH> adapterObservable;

        public DataBlockChangedCallback(IAdapterObservable<T, VH> adapterObservable) {
            this.adapterObservable = adapterObservable;
        }

        @Override
        public void onChanged(DataBlockProvider<T> sender) {
            adapterObservable.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            adapterObservable.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            adapterObservable.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(DataBlockProvider<T> sender, int fromPosition, int toPosition) {
            adapterObservable.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeRemoved(DataBlockProvider<T> sender, int positionStart, int itemCount) {
            adapterObservable.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }
}
