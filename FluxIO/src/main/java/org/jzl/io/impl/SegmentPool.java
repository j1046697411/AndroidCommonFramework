package org.jzl.io.impl;

import org.jzl.lang.util.pool.IPool;
import org.jzl.lang.util.pool.Pools;

public class SegmentPool {

    public static final IPool<Segment> POOL = IPool.of(10, Segment::of);

    static {
        Pools.getInstance().setPool(Segment.class, POOL);
    }

    public static Segment obtain() {
        return POOL.obtain();
    }

    public static void free(Segment segment) {
        POOL.free(segment);
    }

}
