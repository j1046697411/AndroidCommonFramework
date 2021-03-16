package org.jzl.io;

import org.jzl.io.impl.Segment;

import java.io.IOException;

public interface ISegments {

    Segment first();

    Segment last();

    void push(Segment segment);

    void pop();

    int commonCompleteSegmentByteCount();

    Segment commonWritableSegment(int minimumCapacity);

    Segment commonReadableSegment();

    void setSize(int size);

    void require(Segment segment);

    int read(IBuffer buffer, int length) throws IOException;

    int write(IBuffer buffer, int length) throws IOException;

    int indexOf(byte b);

}
