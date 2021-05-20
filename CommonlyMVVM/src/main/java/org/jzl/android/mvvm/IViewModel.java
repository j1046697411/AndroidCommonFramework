package org.jzl.android.mvvm;

import androidx.annotation.NonNull;

public interface IViewModel<V extends IView> {

    void bind(@NonNull V view);

    void initialise();

    void unbind();

}
