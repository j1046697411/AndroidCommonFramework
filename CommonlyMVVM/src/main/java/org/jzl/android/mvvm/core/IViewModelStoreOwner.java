package org.jzl.android.mvvm.core;

import androidx.annotation.NonNull;

public interface IViewModelStoreOwner {

    @NonNull
    IViewModelStore getTargetViewModelStore();

}
