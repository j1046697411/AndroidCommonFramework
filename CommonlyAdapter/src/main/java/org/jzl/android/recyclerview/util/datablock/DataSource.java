package org.jzl.android.recyclerview.util.datablock;

import java.util.List;

public interface DataSource<T> extends List<T> {

    void move(int fromPosition, int toPosition);

    void dirty();

}
