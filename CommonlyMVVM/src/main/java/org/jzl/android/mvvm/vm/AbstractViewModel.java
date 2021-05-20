package org.jzl.android.mvvm.vm;

import androidx.annotation.NonNull;

import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModel;
import org.jzl.android.mvvm.IViewModelProvider;

public abstract class AbstractViewModel<V extends IView> implements IViewModel<V> {

    protected V view;

    @Override
    public final void bind(@NonNull V view) {
        this.view = view;
        bindVariable(view, view.getViewModelProvider());
        preBind(view);
    }

    protected void preBind(@NonNull V view){
    }

    protected void bindVariable(@NonNull V view, @NonNull IViewModelProvider viewModelProvider){
    }

    @Override
    public void initialise() {
    }

    @Override
    public void unbind() {
        view = null;
    }
}
