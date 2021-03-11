package org.jzl.android.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ILayoutViewFactory {

    View createLayoutView(@NonNull LayoutInflater layoutInflater,@Nullable ViewGroup container, boolean attachToRoot);

}
