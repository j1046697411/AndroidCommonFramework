package org.jzl.lang.util.pool;

import org.jzl.lang.util.ArrayUtils;
import org.jzl.lang.util.CollectionUtils;
import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractPool<T> implements IPool<T> {

    private final LinkedList<T> freeObjects;
    private final int maxFreeObjectCount;

    public AbstractPool(int maxFreeObjectCount) {
        this.freeObjects = new LinkedList<>();
        this.maxFreeObjectCount = maxFreeObjectCount;
    }

    @Override
    public T obtain() {
        if (CollectionUtils.nonEmpty(freeObjects)) {
            return freeObjects.pop();
        } else {
            return createObject();
        }
    }

    protected abstract T createObject();

    @Override
    public void free(T object) {
        if (ObjectUtils.isNull(object)) {
            return;
        }
        if (freeObjects.size() < maxFreeObjectCount) {
            freeObjects.add(object);
        }
        reset(object);
    }

    @SafeVarargs
    @Override
    public final void freeAll(T... objects) {
        if (ArrayUtils.nonEmpty(objects)) {
            ForeachUtils.each(objects, this::free);
        }
    }

    @Override
    public void freeAll(Collection<T> objects) {
        if (CollectionUtils.nonEmpty(objects)) {
            ForeachUtils.each(objects, this::free);
        }
    }

    @Override
    public int getFreeCount() {
        return freeObjects.size();
    }

    @Override
    public void clear() {
        freeObjects.clear();
    }

    protected void reset(T object) {
        if (object instanceof IResettable) {
            ((IResettable) object).reset();
        }
    }
}
