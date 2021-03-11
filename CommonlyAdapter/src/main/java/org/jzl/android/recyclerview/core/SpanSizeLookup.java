package org.jzl.android.recyclerview.core;

public interface SpanSizeLookup<T> {

    int getSpanSize(T data, int spanCount, int position);

}
