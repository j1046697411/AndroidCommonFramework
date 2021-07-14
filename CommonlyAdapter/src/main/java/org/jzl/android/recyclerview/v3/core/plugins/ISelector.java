package org.jzl.android.recyclerview.v3.core.plugins;

import org.jzl.android.recyclerview.v3.model.ISelectable;

public interface ISelector<T extends ISelectable> {

    void checked(int position, boolean checked);

    void checked(T data, boolean checked);

    void checkedAll();

    void uncheckedAll();
}
