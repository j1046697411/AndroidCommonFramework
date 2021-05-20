package org.jzl.android.mvvm.v2;

import androidx.annotation.NonNull;

public interface IViewModel<V extends IView> {

    void bind(@NonNull V view);

    void initialise();

    void unbind();

}
