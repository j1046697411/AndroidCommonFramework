package org.jzl.android.mvvm.view;

import android.content.DialogInterface;

import org.jzl.android.mvvm.IView;

public interface IDialogView extends IUniversalView, IView, DialogInterface {

    @Override
    void dismiss();

    @Override
    void cancel();

    boolean isShowing();
}
