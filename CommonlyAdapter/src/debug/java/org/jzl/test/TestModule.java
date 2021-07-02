package org.jzl.test;

import androidx.annotation.NonNull;

import org.jzl.android.recyclerview.v3.core.IConfiguration;
import org.jzl.android.recyclerview.v3.core.IModule;
import org.jzl.android.recyclerview.v3.core.IOptions;
import org.jzl.android.recyclerview.v3.core.IViewHolder;

public class TestModule implements IModule<String, TestModule.TestViewHolder> {


    @NonNull
    @Override
    public IOptions<String, TestViewHolder> setup(@NonNull IConfiguration<?, ?> configuration) {
        return configuration
                .options(this, (options, itemView, itemViewType) -> new TestViewHolder())

                .build();
    }

    public static class TestViewHolder implements IViewHolder {
    }
}
