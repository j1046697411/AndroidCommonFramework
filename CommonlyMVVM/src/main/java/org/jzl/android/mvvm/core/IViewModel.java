package org.jzl.android.mvvm.core;

import androidx.annotation.NonNull;

public interface IViewModel {

    void bind(@NonNull IView view);

    void initialise();

    void unbind();

}
