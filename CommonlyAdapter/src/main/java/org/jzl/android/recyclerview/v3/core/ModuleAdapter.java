package org.jzl.android.recyclerview.v3.core;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.v3.core.module.IAdapterModule;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.android.recyclerview.v3.core.vh.ModuleAdapterViewHolder;

import java.util.List;

public class ModuleAdapter<T, VH extends IViewHolder> extends
        RecyclerView.Adapter<ModuleAdapterViewHolder<T, VH>> implements IModule<T, VH> {

    @NonNull
    private final IConfiguration<T, VH> configuration;

    @NonNull
    private final IAdapterModule<T, VH> adapterModule;

    public ModuleAdapter(@NonNull IConfiguration<T, VH> configuration) {
        this.configuration = configuration;
        this.adapterModule = configuration.getAdapterModule();
    }

    @NonNull
    @Override
    public IOptions<T, VH> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<T> dataGetter) {
        return adapterModule.setup(configuration, dataGetter);
    }

    @NonNull
    @Override
    public ModuleAdapterViewHolder<T, VH> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolder(configuration.getOptions(), parent, viewType);
    }

    //创建viewHolder逻辑
    private ModuleAdapterViewHolder<T, VH> createViewHolder(IOptions<?, VH> options, ViewGroup parent, int viewType) {
        IViewFactoryOwner<VH> viewFactoryOwner = options.get(viewType);
        ModuleAdapterViewHolder<T, VH> moduleAdapterViewHolder;
        if (viewFactoryOwner.getOptions() == options) {//说明是module自己添加的模块，需要用module自己的逻辑去处理
            IViewFactory viewFactory = viewFactoryOwner.getViewFactory();
            IViewHolderFactory<VH> viewHolderFactory = options.getViewHolderFactory();
            View itemView = viewFactory.create(configuration.getLayoutInflater(), parent, viewType);
            VH viewHolder = viewHolderFactory.createViewHolder(options, itemView, viewType);
            moduleAdapterViewHolder = createViewHolder(options, itemView, viewHolder);
        } else {
            moduleAdapterViewHolder = createViewHolder(viewFactoryOwner.getOptions(), parent, viewType);
        }
        options.notifyCreatedViewHolder(moduleAdapterViewHolder, viewType);
        return moduleAdapterViewHolder;
    }

    private ModuleAdapterViewHolder<T, VH> createViewHolder(IOptions<?, VH> options, View itemView, VH viewHolder) {
        return new ModuleAdapterViewHolder<>(options, itemView, viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapterViewHolder<T, VH> holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapterViewHolder<T, VH> holder, int position, @NonNull List<Object> payloads) {
        holder.binding(configuration.getOptions().getDataBinder(), configuration.getDataGetter().getData(position), payloads);
    }

    @Override
    public int getItemCount() {
        return adapterModule.getItemCount(configuration);
    }

    @Override
    public int getItemViewType(int position) {
        return adapterModule.getItemViewType(configuration, position);
    }

    @Override
    public long getItemId(int position) {
        return adapterModule.getItemId(configuration, position);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ModuleAdapterViewHolder<T, VH> holder) {
        super.onViewAttachedToWindow(holder);
        configuration.getOptions().notifyViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ModuleAdapterViewHolder<T, VH> holder) {
        super.onViewDetachedFromWindow(holder);
        configuration.getOptions().notifyViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        configuration.getOptions().notifyAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        configuration.getOptions().notifyDetachedFromRecyclerView(recyclerView);
    }


    @Override
    public void onViewRecycled(@NonNull ModuleAdapterViewHolder<T, VH> holder) {
        super.onViewRecycled(holder);
        configuration.getOptions().notifyViewRecycled(holder);
    }
}
