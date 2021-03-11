package org.jzl.android.recyclerview.core.factory;

import androidx.recyclerview.widget.RecyclerView;

import org.jzl.android.recyclerview.core.configuration.Configuration;
import org.jzl.android.recyclerview.core.observer.AdapterDataObservable;

public interface AdapterDataObserverFactory<T, VH extends RecyclerView.ViewHolder> {

    AdapterDataObservable createAdapterDataObserver(Configuration<T, VH> configuration, RecyclerView.Adapter<VH> adapter);

}
