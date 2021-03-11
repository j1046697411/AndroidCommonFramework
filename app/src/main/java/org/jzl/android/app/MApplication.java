package org.jzl.android.app;

import android.app.Application;

import org.jzl.android.mvvm.M;

public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        M.initialise(this);
    }
}
