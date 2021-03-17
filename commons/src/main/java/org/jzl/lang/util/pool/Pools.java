package org.jzl.lang.util.pool;

import org.jzl.lang.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public class Pools {

    private final Map<Class<?>, IPool<?>> pools = new HashMap<>();
    private int maxFreeObjectCount = 100;

    private Pools() {
    }

    public static Pools getInstance() {
        return Holder.SIN;
    }

    public void setMaxFreeObjectCount(int maxFreeObjectCount) {
        this.maxFreeObjectCount = maxFreeObjectCount;
    }

    public <T> void setPool(Class<T> type, IPool<T> pool) {
        if (ObjectUtils.nonNull(type) && ObjectUtils.nonNull(pool)) {
            this.pools.put(type, pool);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> IPool<T> getPool(Class<T> type) {
        IPool<T> pool = (IPool<T>) pools.get(type);
        if (ObjectUtils.isNull(pool)) {
            pool = IPool.of(maxFreeObjectCount, type);
            setPool(type, pool);
        }
        return pool;
    }

    private static class Holder {
        private static final Pools SIN = new Pools();
    }
}
