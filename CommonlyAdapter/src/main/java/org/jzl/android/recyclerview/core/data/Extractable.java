package org.jzl.android.recyclerview.core.data;

import androidx.annotation.IdRes;

public interface Extractable {

    <E> E getExtra(@IdRes int key);

    <E> E getExtra(@IdRes int key, E defValue);

    void putExtra(@IdRes int key, Object value);

    boolean hasExtra(int key);

    void removeExtra(@IdRes int key);
}