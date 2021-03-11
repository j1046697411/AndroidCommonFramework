package org.jzl.android.recyclerview.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jzl.android.recyclerview.BuildConfig;

public interface Logger {

    int v(@NonNull String msg);

    int v(@Nullable String msg, @Nullable Throwable tr);

    int d(@NonNull String msg);

    int d(@Nullable String msg, @Nullable Throwable tr);

    int i(@NonNull String msg);

    int i(@Nullable String msg, @Nullable Throwable tr);

    int w(@NonNull String msg);

    int w(@Nullable String msg, @Nullable Throwable tr);

    int w(@Nullable Throwable tr);

    int e(@NonNull String msg);

    int e(@Nullable String msg, @Nullable Throwable tr);

    static Logger logger(Class<?> type) {
        return BuildConfig.DEBUG ? new AndroidLogger(type.getSimpleName()) : new NullableLogger();
    }

}
