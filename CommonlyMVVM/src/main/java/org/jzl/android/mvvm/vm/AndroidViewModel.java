package org.jzl.android.mvvm.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import org.jzl.android.mvvm.core.IView;

public class AndroidViewModel extends AbstractViewModel {

    protected final Application application;
    protected final String key;

    public AndroidViewModel(@NonNull Application application, @NonNull String key) {
        this.application = application;
        this.key = key;
    }

    @Override
    protected void bindVariable(IView view) {
        super.bindVariable(view);
    }
}
