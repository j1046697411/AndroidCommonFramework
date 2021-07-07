package org.jzl.android.recyclerview.v3.model;

import android.os.Build;
import android.util.SparseArray;

import org.jzl.lang.util.ObjectUtils;

public class ExtraModel implements IExtractable {

    private final SparseArray<Object> extras;

    public ExtraModel() {
        this(new SparseArray<>());
    }

    public ExtraModel(SparseArray<Object> extras) {
        this.extras = extras;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E getExtra(int key) {
        return (E) extras.get(key);
    }

    @Override
    public <E> E getExtra(int key, E defValue) {
        return ObjectUtils.get(getExtra(key), defValue);
    }

    @Override
    public void putExtra(int key, Object value) {
        extras.put(key, value);
    }

    @Override
    public boolean hasExtra(int key) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return extras.contains(key);
        } else {
            return ObjectUtils.nonNull(extras.get(key));
        }
    }

    @Override
    public void removeExtra(int key) {
        extras.remove(key);
    }
}