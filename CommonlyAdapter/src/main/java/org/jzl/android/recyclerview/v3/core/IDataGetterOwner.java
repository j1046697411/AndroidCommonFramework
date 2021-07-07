package org.jzl.android.recyclerview.v3.core;

import androidx.annotation.NonNull;

public interface IDataGetterOwner<T> {

    @NonNull
    IDataGetter<T> getDataGetter();

}
