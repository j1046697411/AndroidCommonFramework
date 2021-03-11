package org.jzl.android.mvvm;

import androidx.annotation.NonNull;

public interface IViewModel {

    void bind(@NonNull IView view);

    void unbind();
}
