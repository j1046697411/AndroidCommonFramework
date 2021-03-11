package org.jzl.android.recyclerview.core.diff;

import java.util.List;

public interface IDiffDispatcherUpdatable<T> {

    void replaceAll(List<T> newData, boolean notify);

    void replaceAll(List<T> newData);

}
