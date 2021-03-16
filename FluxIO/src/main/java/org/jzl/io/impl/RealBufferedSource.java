package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.IBufferedSource;
import org.jzl.io.IByteString;
import org.jzl.io.ISegments;
import org.jzl.io.ISink;
import org.jzl.io.ISource;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class RealBufferedSource implements IBufferedSource {

    private final Buffer buffer = new Buffer();
    private final ISource source;

    public RealBufferedSource(ISource source) {
        this.source = source;
    }

    @Override
    public IBuffer buffer() {
        return buffer;
    }

    @Override
    public boolean exhausted() {
        return buffer.exhausted();
    }

    @Override
    public int read(IBuffer buffer, int byteCount) throws IOException {
        readCompleteSegment();
        return this.buffer.read(buffer, Math.min(byteCount, this.buffer.size()));
    }

    @Override
    public void close() throws IOException {
        source.close();
    }


    @Override
    public int readAll(ISink sink) throws IOException {
        int length = 0;
        for (; ; ) {
            int readLength = readCompleteSegment();
            if (readLength == 0) {
                break;
            }else{
                sink.write(this.buffer, readLength);
                length += readLength;
            }
        }
        if (buffer.size() != 0) {
            length += buffer.readAll(sink);
        }
        return length;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return read(bytes, 0, bytes.length);
    }

    @Override
    public int read(byte[] bytes, int offset, int byteCount) throws IOException {
        int length = readCompleteSegment();
        return buffer.read(bytes, offset, Math.min(byteCount, length));
    }

    @Override
    public int readFully(byte[] bytes) throws IOException {
        return readFully(bytes, 0, bytes.length);
    }

    @Override
    public int readFully(byte[] bytes, int offset, int byteCount) throws IOException {
        commonRequire(byteCount);
        return buffer.readFully(bytes, offset, byteCount);
    }

    @Override
    public byte readByte() throws IOException {
        commonRequire(1);
        return buffer.readByte();
    }

    @Override
    public short readShort() throws IOException {
        commonRequire(2);
        return buffer.readShort();
    }

    @Override
    public int readInt() throws IOException {
        commonRequire(4);
        return buffer.readInt();
    }

    @Override
    public long readLong() throws IOException {
        commonRequire(8);
        return buffer.readLong();
    }

    @Override
    public byte[] readByteArray(int byteCount) throws IOException {
        commonRequire(byteCount);
        return buffer.readByteArray(byteCount);
    }

    @Override
    public byte[] readByteArray() throws IOException {
        readAll();
        return buffer.readByteArray();
    }

    @Override
    public ByteBuffer readByteBuffer() throws IOException {
        readCompleteSegment();
        return buffer.readByteBuffer();
    }

    @Override
    public ByteBuffer readByteBuffer(int length) throws IOException {
        readCompleteSegment();
        return buffer.readByteBuffer(length);
    }

    @Override
    public IByteString readByteString(int length) throws IOException {
        commonRequire(length);
        return buffer.readByteString(length);
    }

    @Override
    public IByteString readByteString() throws IOException {
        readAll();
        return buffer.readByteString();
    }

    @Override
    public String readString(Charset charset) throws IOException {
        readAll();
        return buffer.readString(charset);
    }

    @Override
    public String readString(int byteCount, Charset charset) throws IOException {
        readAll();
        return buffer.readString(byteCount, charset);
    }

    @Override
    public IBufferedSource skip(int length) throws IOException {
        commonRequire(length);
        return buffer.skip(length);
    }

    @Override
    public InputStream inputStream() {
        return new BufferedSourceInputStream(this);
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        readCompleteSegment();
        return buffer.read(byteBuffer);
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    private int readCompleteSegment() throws IOException {
        if (buffer.size == 0) {
            ISegments segments = this.buffer.segments();
            while (segments.commonCompleteSegmentByteCount() == 0) {
                if (source.read(this.buffer, Segment.SIZE) == -1) {
                    break;
                }
            }
            return segments.commonCompleteSegmentByteCount();
        } else {
            return buffer.size();
        }
    }

    private boolean require(int byteCount) throws IOException {
        while (buffer.size < byteCount) {
            if (source.read(buffer, Segment.SIZE) == -1) {
                return false;
            }
        }
        return true;
    }

    private void readAll() throws IOException {
        buffer.writeAll(source);
    }

    private void commonRequire(int byteCount) throws IOException {
        if (!require(byteCount)) {
            throw new EOFException();
        }
    }

}
