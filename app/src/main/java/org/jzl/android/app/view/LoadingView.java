package org.jzl.android.app.view;

import androidx.annotation.NonNull;

import org.jzl.android.app.MainActivity;
import org.jzl.android.mvvm.IView;
import org.jzl.android.mvvm.view.AbstractView;

public class LoadingView extends AbstractView<MainActivity> implements IView {

    public LoadingView(@NonNull MainActivity parentView) {
        super(parentView);
    }
}
