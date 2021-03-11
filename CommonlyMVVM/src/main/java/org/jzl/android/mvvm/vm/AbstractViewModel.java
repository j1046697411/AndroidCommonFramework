package org.jzl.android.mvvm.vm;

import androidx.annotation.NonNull;

import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;


public class AbstractViewModel implements IViewModel {

    protected IView view;

    @Override
    public final void bind(@NonNull IView view) {
        this.view = view;
        initialise(view);
    }

    protected void initialise(IView view) {
    }

    @Override
    public void unbind() {
        view = null;
    }
}
