package org.jzl.android.recyclerview.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class AndroidLogger implements Logger {

    private final String tag;

    public AndroidLogger(@NonNull String tag) {
        this.tag = tag;
    }

    public int v(@NonNull String msg) {
        return Log.v(tag, msg);
    }

    public int v(@Nullable String msg, @Nullable Throwable tr) {
        return Log.v(tag, msg, tr);
    }

    public int d( @NonNull String msg) {
        return Log.d(tag, msg);
    }

    public  int d(@Nullable String msg, @Nullable Throwable tr) {
        return Log.d(tag, msg, tr);
    }

    public  int i( @NonNull String msg) {
        return Log.i(tag, msg);
    }

    public  int i( @Nullable String msg, @Nullable Throwable tr) {
        return Log.i(tag, msg, tr);
    }

    public  int w(@NonNull String msg) {
        return Log.w(tag, msg);
    }

    public  int w( @Nullable String msg, @Nullable Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    public  int w(@Nullable Throwable tr) {
        return Log.w(tag, tr);
    }

    public  int e( @NonNull String msg) {
        return Log.e(tag, msg);
    }

    public  int e(@Nullable String msg, @Nullable Throwable tr) {
        return Log.e(tag, msg, tr);
    }
}
