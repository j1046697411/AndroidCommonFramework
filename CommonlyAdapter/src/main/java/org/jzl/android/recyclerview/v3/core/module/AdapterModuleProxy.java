package org.jzl.android.recyclerview.v3.core.module;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AdapterModuleProxy<T, VH extends IViewHolder> implements IAdapterModuleProxy<T, VH> {

    private final List<IProxy<T, VH>> proxies = new ArrayList<>();
    private boolean dirty = false;

    @NonNull
    @Override
    public IAdapterModule<T, VH> proxy(@NonNull IConfiguration<T, VH> configuration, @NonNull IAdapterModule<T, VH> adapterModule) {
        if (dirty) {
            Collections.sort(proxies, Collections.reverseOrder());
            dirty = false;
        }
        IAdapterModule<T, VH> newAdapterModule = adapterModule;
        for (IProxy<T, VH> proxy : proxies){
            newAdapterModule = proxy.proxy(configuration, newAdapterModule);
        }
        return newAdapterModule;
    }

    @Override
    public void addProxy(@NonNull IProxy<T, VH> proxy) {
        if (!proxies.contains(proxy)) {
            proxies.add(proxy);
            dirty = true;
        }
    }
}
