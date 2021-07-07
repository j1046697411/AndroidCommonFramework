package org.jzl.android.recyclerview.v3.model;

import androidx.annotation.IdRes;

public interface IExtractable {

    <E> E getExtra(@IdRes int key);

    <E> E getExtra(@IdRes int key, E defValue);

    void putExtra(@IdRes int key, Object value);

    boolean hasExtra(@IdRes int key);

    void removeExtra(@IdRes int key);
}
