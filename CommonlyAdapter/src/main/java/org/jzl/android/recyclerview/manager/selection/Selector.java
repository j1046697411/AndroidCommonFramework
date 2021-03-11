package org.jzl.android.recyclerview.manager.selection;

import org.jzl.android.recyclerview.core.data.Selectable;

public interface Selector<T extends Selectable>{

    void checked(int position, boolean checked);

    void checked(T data, boolean checked);

    void checkedAll();

    void uncheckedAll();
}