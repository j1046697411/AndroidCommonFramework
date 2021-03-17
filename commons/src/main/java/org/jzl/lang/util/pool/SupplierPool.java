package org.jzl.lang.util.pool;

import org.jzl.lang.fun.Supplier;

class SupplierPool<T> extends AbstractPool<T> {

    private final Supplier<T> supplier;

    SupplierPool(int maxFreeObjectCount, Supplier<T> supplier) {
        super(maxFreeObjectCount);
        this.supplier = supplier;
    }

    @Override
    protected T createObject() {
        return supplier.get();
    }
}
