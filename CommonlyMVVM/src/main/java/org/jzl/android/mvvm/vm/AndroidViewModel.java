package org.jzl.android.mvvm.vm;

import android.app.Application;

import androidx.annotation.NonNull;

public class AndroidViewModel extends AbstractViewModel {

    protected final Application application;
    protected final String key;

    public AndroidViewModel(@NonNull Application application, @NonNull String key) {
        this.application = application;
        this.key = key;
    }

}
