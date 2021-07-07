package org.jzl.test;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IDataGetter;
import org.jzl.android.recyclerview.v3.core.module.IModule;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

public class TestModule implements IModule<String, TestModule.TestViewHolder> {

    @NonNull
    @Override
    public IOptions<String, TestViewHolder> setup(@NonNull IConfiguration<?, ?> configuration, @NonNull IDataGetter<String> dataGetter) {
        return configuration
                .options(this, (options, itemView, itemViewType) -> new TestViewHolder(), dataGetter)
                .build();
    }

    public static class TestViewHolder implements IViewHolder {
    }
}
