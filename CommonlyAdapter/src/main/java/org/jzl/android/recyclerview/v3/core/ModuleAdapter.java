package org.jzl.android.recyclerview.v3.core;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ModuleAdapter<T, VH extends IViewHolder> extends RecyclerView.Adapter<ModuleAdapter.ModuleAdapterViewHolder<T, VH>> {

    @NonNull
    private final IConfiguration<T, VH> configuration;

    @NonNull
    private final IAdapterModule<T, VH> adapterModule;

    private IOptions<T, VH> options;

    public ModuleAdapter(@NonNull IConfiguration<T, VH> configuration) {
        this.configuration = configuration;
        this.adapterModule = configuration.getAdapterModule();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        options = adapterModule.setup(configuration);
    }

    @NonNull
    @Override
    public ModuleAdapterViewHolder<T, VH> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return options.createViewHolder(configuration, parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapterViewHolder<T, VH> holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapterViewHolder<T, VH> holder, int position, @NonNull List<Object> payloads) {
        holder.binding(options.getDataBinder(), adapterModule.getItemData(configuration, configuration.getDataProvider(), position), payloads);
    }

    @Override
    public int getItemCount() {
        return adapterModule.getItemCount(configuration, configuration.getDataProvider());
    }

    @Override
    public int getItemViewType(int position) {
        return adapterModule.getItemViewType(configuration, configuration.getDataProvider(), position);
    }

    @Override
    public long getItemId(int position) {
        return adapterModule.getItemId(configuration, configuration.getDataProvider(), position);
    }

    public static class ModuleAdapterViewHolder<T, VH extends IViewHolder> extends RecyclerView.ViewHolder implements IContext {

        @NonNull
        private final IOptions<?, ?> options;

        @NonNull
        private final VH viewHolder;

        private List<Object> payloads;


        public ModuleAdapterViewHolder(@NonNull IOptions<?, ?> options, @NonNull View itemView, @NonNull VH viewHolder) {
            super(itemView);
            this.options = options;
            this.viewHolder = viewHolder;
        }

        public void binding(IDataBinder<T, VH> dataBinder, T data, @NonNull List<Object> payloads) {
            this.payloads = payloads;
            dataBinder.binding(this, viewHolder, data);
        }

        @NonNull
        @Override
        public IConfiguration<?, ?> getConfiguration() {
            return options.getConfiguration();
        }

        @NonNull
        @Override
        public IOptions<?, ?> getOptions() {
            return options;
        }

        @NonNull
        @Override
        public List<Object> getPayloads() {
            return payloads;
        }

        @NonNull
        public VH getViewHolder() {
            return viewHolder;
        }
    }
}
