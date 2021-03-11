package org.jzl.android.recyclerview.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class NullableLogger implements Logger {
    @Override
    public int v(@NonNull String msg) {
        return 0;
    }

    @Override
    public int v(@Nullable String msg, @Nullable Throwable tr) {
        return 0;
    }

    @Override
    public int d(@NonNull String msg) {
        return 0;
    }

    @Override
    public int d(@Nullable String msg, @Nullable Throwable tr) {
        return 0;
    }

    @Override
    public int i(@NonNull String msg) {
        return 0;
    }

    @Override
    public int i(@Nullable String msg, @Nullable Throwable tr) {
        return 0;
    }

    @Override
    public int w(@NonNull String msg) {
        return 0;
    }

    @Override
    public int w(@Nullable String msg, @Nullable Throwable tr) {
        return 0;
    }

    @Override
    public int w(@Nullable Throwable tr) {
        return 0;
    }

    @Override
    public int e(@NonNull String msg) {
        return 0;
    }

    @Override
    public int e(@Nullable String msg, @Nullable Throwable tr) {
        return 0;
    }
}
