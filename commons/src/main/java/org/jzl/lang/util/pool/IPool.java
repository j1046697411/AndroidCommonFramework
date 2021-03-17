package org.jzl.lang.util.pool;

import org.jzl.lang.fun.Supplier;

import java.util.Collection;

public interface IPool<T> {

    static <T> IPool<T> of(int maxFreeObjectCount, Supplier<T> supplier) {
        return new SupplierPool<T>(maxFreeObjectCount, supplier);
    }

    static <T> IPool<T> of(int maxFreeObjectCount, Class<T> type) {
        return new ReflectionPool<>(maxFreeObjectCount, type);
    }

    T obtain();

    void free(T object);

    @SuppressWarnings("all")
    void freeAll(T... objects);

    void freeAll(Collection<T> objects);

    int getFreeCount();

    void clear();

}
