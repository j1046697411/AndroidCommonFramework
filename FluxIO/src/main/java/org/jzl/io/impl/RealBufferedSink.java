package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.IBufferedSink;
import org.jzl.io.IByteString;
import org.jzl.io.ISegments;
import org.jzl.io.ISink;
import org.jzl.io.ISource;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class RealBufferedSink implements IBufferedSink {

    private final Buffer buffer = new Buffer();
    private final ISink sink;

    public RealBufferedSink(ISink sink) {
        this.sink = sink;
    }

    @Override
    public IBuffer buffer() {
        return buffer;
    }

    @Override
    public RealBufferedSink writeAll(ISource source) throws IOException {
        while (-1 != source.read(buffer, Segment.SIZE)) {
            emitCompleteSegments();
        }
        return this;
    }

    @Override
    public IBufferedSink write(byte[] bytes) throws IOException {
        buffer.write(bytes);
        return emitCompleteSegments();
    }

    @Override
    public IBufferedSink write(byte[] bytes, int offset, int byteCount) throws IOException {
        buffer.write(bytes, offset, byteCount);
        return emitCompleteSegments();
    }

    @Override
    public IBufferedSink write(IByteString byteString) throws IOException {
        buffer.write(byteString);
        return emitCompleteSegments();
    }

    @Override
    public IBufferedSink write(IByteString byteString, int offset, int length) throws IOException {
        buffer.write(byteString, offset, length);
        return emitCompleteSegments();
    }

    @Override
    public IBufferedSink writeByte(int b) throws IOException {
        buffer.writeByte(b);
        return emitCompleteSegments();
    }

    @Override
    public IBufferedSink writeShort(int s) throws IOException {
        buffer.writeShort(s);
        return emitCompleteSegments();
    }

    @Override
    public IBufferedSink writeInt(int i) throws IOException {
        buffer.writeInt(i);
        return emitCompleteSegments();
    }

    @Override
    public IBufferedSink writeLong(long l) throws IOException {
        buffer.writeLong(l);
        return emitCompleteSegments();
    }

    @Override
    public OutputStream outputStream() {
        return new BufferedSinkOutputStream(this);
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        int length = buffer.write(byteBuffer);
        emitCompleteSegments();
        return length;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public IBufferedSink write(IBuffer buffer, int byteCount) throws IOException {
        this.buffer.write(buffer, byteCount);
        return emitCompleteSegments();
    }

    @Override
    public void close() throws IOException {
        flush();
        sink.close();
    }

    @Override
    public void flush() throws IOException {
        if (buffer.size > 0) {
            buffer.readAll(sink);
        }
        sink.flush();
    }

    private IBufferedSink emitCompleteSegments() throws IOException {
        ISegments segments = buffer.segments();
        for (; ; ) {
            int completeSegmentByteCount = segments.commonCompleteSegmentByteCount();
            if (completeSegmentByteCount == 0) {
                break;
            }
            sink.write(buffer, completeSegmentByteCount);
        }
        return this;
    }
}
