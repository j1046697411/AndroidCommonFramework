package org.jzl.android.mvvm.view;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import org.jzl.android.mvvm.IView;

public interface IActivityView extends IView, IUniversalView {

    @NonNull
    FragmentManager getSupportFragmentManager();

    Intent getIntent();

    void setResult(int requestCode);

    void setResult(int requestCode, Intent intent);

    void finish();

    void finishActivity(int requestCode);
}
