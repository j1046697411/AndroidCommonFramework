package org.jzl.android.mvvm.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.IViewModelProvider;

public class AbstractView<V extends IView> implements IView {

    protected final V parentView;

    protected AbstractView(@NonNull V parentView) {
        this.parentView = parentView;
    }

    @Override
    public Application getApplication() {
        return parentView.getApplication();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return parentView.getLifecycle();
    }

    @NonNull
    @Override
    public IViewModelProvider getViewModelProvider() {
        return parentView.getViewModelProvider();
    }

    @Override
    public void bindVariable(int variableId, @Nullable Object value) {
        parentView.bindVariable(variableId, value);
    }

    @Override
    public void unbind() {
    }
}
