package org.jzl.io.impl;

import java.util.Arrays;

public class SegmentPool {

    public static Segment obtain(){
        return Segment.of();
    }

    public static void free(Segment segment){
        System.out.println( segment.isShared() + "|" + Arrays.toString(segment.bytes));
    }

}
