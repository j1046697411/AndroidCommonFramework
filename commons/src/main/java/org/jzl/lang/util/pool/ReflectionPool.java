package org.jzl.lang.util.pool;

class ReflectionPool<T> extends AbstractPool<T> {

    private final Class<T> type;

    ReflectionPool(int maxFreeObjectCount, Class<T> type) {
        super(maxFreeObjectCount);
        this.type = type;
    }

    @Override
    protected T createObject() {
        try {

            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
