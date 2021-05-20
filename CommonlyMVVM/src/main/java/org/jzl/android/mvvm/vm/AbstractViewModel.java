package org.jzl.android.mvvm.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;

import org.jzl.android.mvvm.core.IView;
import org.jzl.android.mvvm.core.IViewModel;

public class AbstractViewModel implements IViewModel , LifecycleObserver {

    protected IView view;

    @Override
    public final void bind(@NonNull IView view) {
        this.view = view;
        bindVariable(view);
        preBind(view);
    }

    protected void preBind(@NonNull IView view) {
    }

    protected void bindVariable(@NonNull IView view) {
    }

    @Override
    public void initialise() {
    }

    @Override
    public void unbind() {
        view = null;
    }
}
