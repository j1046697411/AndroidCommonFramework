package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.ISegments;
import org.jzl.lang.util.ObjectUtils;

import java.io.IOException;
import java.util.Arrays;

public class BufferSegments implements ISegments {

    private Segment segment;
    private final Buffer buffer;

    public BufferSegments(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public Segment first() {
        return segment;
    }

    @Override
    public Segment last() {
        return ObjectUtils.isNull(segment) ? null : segment.prev;
    }

    @Override
    public void push(Segment last) {
        if (ObjectUtils.isNull(last)) {
            return;
        }
        if (ObjectUtils.isNull(segment)) {
            this.segment = last;
        } else {
            this.segment.before(last);
        }
    }

    @Override
    public void pop() {
        if (ObjectUtils.nonNull(segment)) {
            this.segment = this.segment.pop();
        }
    }

    @Override
    public int commonCompleteSegmentByteCount() {
        if (ObjectUtils.isNull(segment)) {
            return 0;
        }
        if (segment.remaining() == 0 || segment.isShared() || segment.hasNext()) {
            return segment.length();
        }
        return 0;
    }

    @Override
    public Segment commonWritableSegment(int minimumCapacity) {
        if (minimumCapacity > Segment.SIZE) {
            throw new RuntimeException(String.format("minimumCapacity %d > %d : ", minimumCapacity, Segment.SIZE));
        }
        Segment last = last();
        if (ObjectUtils.nonNull(last) && last.remaining() >= minimumCapacity) {
            return last;
        } else {
            Segment segment = SegmentPool.obtain();
            push(segment);
            return segment;
        }
    }

    @Override
    public Segment commonReadableSegment() {
        return first();
    }

    @Override
    public void setSize(int size) {
        this.buffer.size = size;
    }

    @Override
    public void require(Segment segment) {
        if (ObjectUtils.nonNull(segment) && segment.length() == 0) {
            if (!segment.hasNext() && segment.remaining() >= Segment.SHARE_MINIMUM) {
                return;
            }
            if (segment == this.segment) {
                this.segment = segment.pop();
            } else {
                segment.pop();
            }
            if (!segment.isShared()) {
                SegmentPool.free(segment);
            } else {
                System.out.println("require -> " + segment.isShared() + "|" + Arrays.toString(segment.bytes));
            }

        }
    }

    @Override
    public int read(IBuffer buffer, int length) throws IOException {
        int readLength = buffer.segments().write(this.buffer, length);
        if (readLength == 0) {
            return -1;
        }
        return readLength;
    }

    @Override
    public int write(IBuffer buffer, int length) throws IOException {
        ISegments segments = buffer.segments();
        int whileLength = length;
        while (whileLength > 0) {
            Segment segment = segments.commonReadableSegment();
            if (length >= segment.length()) {
                segments.pop();
                this.push(segment);
                whileLength -= segment.length();
            } else {
                if (segment.length() >= Segment.SHARE_MINIMUM) {
                    this.push(segment.copySegment());
                    whileLength -= segment.length();
                } else {
                    Segment writeSegment = this.commonWritableSegment(Math.min(segment.length(), Segment.SHARE_MINIMUM));
                    whileLength -= segment.read(writeSegment, Math.min(length, writeSegment.remaining()));
                }
            }
        }
        return length;
    }

    @Override
    public int indexOf(byte b) {
        Segment segment = this.segment;
        if (ObjectUtils.isNull(segment)) {
            return -1;
        }
        int size = 0;
        do {
            int index = segment.indexOf(b);
            if (index != -1) {
                return size + index;
            } else {
                size += segment.length();
            }
        } while (segment.hasNext() && ObjectUtils.nonNull(segment = segment.next));
        return -1;
    }

}
