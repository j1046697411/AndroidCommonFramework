package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.IByteString;
import org.jzl.io.ISegments;
import org.jzl.io.ISink;
import org.jzl.io.ISource;
import org.jzl.lang.util.ObjectUtils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Buffer implements IBuffer {

    private static final ThreadLocal<byte[]> BYTES = new ThreadLocal<byte[]>() {
        @Override
        protected byte[] initialValue() {
            return new byte[8];
        }
    };

    int size;
    private final ISegments segments = new BufferSegments(this);

    @Override
    public ISegments segments() {
        return segments;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int read(IBuffer buffer, int byteCount) throws IOException {
        return segments.read(buffer, byteCount);
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        Segment segment = commonReadableSegment();
        if (ObjectUtils.isNull(segment) || segment.length() == 0) {
            return -1;
        }
        int length = segment.read(byteBuffer);
        require(segment);
        if (length != -1) {
            this.size -= length;
        }
        return length;
    }

    @Override
    public int readAll(ISink sink) throws IOException {
        if (size == 0) {
            return -1;
        }
        int length = size;
        sink.write(this, length);
        sink.flush();
        this.size = 0;
        return length;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return read(bytes, 0, bytes.length);
    }

    @Override
    public int read(byte[] bytes, int offset, int byteCount) throws IOException {
        Segment segment = commonReadableSegment();
        if (ObjectUtils.isNull(segment) || segment.length() == 0) {
            return -1;
        }
        int length = segment.read(bytes, offset, byteCount);
        require(segment);
        if (length != -1) {
            this.size -= length;
        }
        return length;
    }

    @Override
    public int readFully(byte[] bytes) throws IOException {
        return readFully(bytes, 0, bytes.length);
    }

    @Override
    public int readFully(byte[] bytes, int offset, int byteCount) throws IOException {
        int length = 0;
        while (length < byteCount) {
            int readLength = read(bytes, length + offset, byteCount - length);
            if (readLength == -1) {
                throw new EOFException();
            }
            length += readLength;
        }
        return length;
    }

    @Override
    public byte readByte() throws IOException {
        Segment segment = commonReadableSegment();
        if (ObjectUtils.isNull(segment) || segment.length() == 0) {
            throw new EOFException();
        }
        byte b = segment.bytes[segment.position++];
        this.size--;
        require(segment);
        return b;
    }

    @Override
    public short readShort() throws IOException {
        byte[] bytes = BYTES.get();
        readFully(bytes, 0, 2);
        return (short) ((bytes[0] & 0xff << 8) | (bytes[1] & 0xff));
    }

    @Override
    public int readInt() throws IOException {
        byte[] bytes = BYTES.get();
        readFully(bytes, 0, 4);
        return (bytes[0] & 0xff << 24)
                | (bytes[1] & 0xff << 16)
                | (bytes[2] & 0xff << 8)
                | (bytes[3] & 0xff);
    }

    @Override
    public long readLong() throws IOException {
        byte[] bytes = BYTES.get();
        readFully(bytes, 0, 8);
        return (bytes[0] & 0xffL << 56)
                | (bytes[1] & 0xffL << 48)
                | (bytes[2] & 0xffL << 40)
                | (bytes[3] & 0xffL << 32)
                | (bytes[4] & 0xffL << 24)
                | (bytes[5] & 0xffL << 16)
                | (bytes[6] & 0xffL << 8)
                | (bytes[7] & 0xffL);
    }

    @Override
    public byte[] readByteArray(int byteCount) throws IOException {
        byte[] bytes = new byte[byteCount];
        readFully(bytes);
        return bytes;
    }

    @Override
    public byte[] readByteArray() throws IOException {
        return readByteArray(size);
    }

    @Override
    public ByteBuffer readByteBuffer() throws IOException {
        Segment segment = commonReadableSegment();
        if (ObjectUtils.nonNull(segment) && segment.length() != 0) {
            ByteBuffer byteBuffer = segment.asByteBuffer();
            int length = segment.skip(segment.length());
            require(segment);
            this.size -= length;
            return byteBuffer;
        }
        return null;
    }

    @Override
    public ByteBuffer readByteBuffer(int length) throws IOException {
        Segment segment = commonReadableSegment();
        if (ObjectUtils.nonNull(segment) && segment.length() != 0) {
            int readLength = Math.min(length, segment.length());
            ByteBuffer byteBuffer = segment.asByteBuffer(readLength);
            int skipLength = segment.skip(readLength);
            require(segment);
            this.size -= skipLength;
            return byteBuffer;
        }
        return null;
    }

    @Override
    public IByteString readByteString(int length) throws IOException {
        return IByteString.wrap(readByteArray(length));
    }

    @Override
    public IByteString readByteString() throws IOException {
        return readByteString(size());
    }

    @Override
    public String readString(Charset charset) throws IOException {
        return new String(readByteArray(), charset);
    }

    @Override
    public String readString(int byteCount, Charset charset) throws IOException {
        return new String(readByteArray(byteCount), charset);
    }

    @Override
    public int indexOf(byte b) throws IOException {
        return segments.indexOf(b);
    }

    @Override
    public IBuffer buffer() {
        return this;
    }

    @Override
    public boolean exhausted() {
        return size == 0;
    }

    @Override
    public IBuffer write(IBuffer buffer, int byteCount) throws IOException {
        segments.write(buffer, byteCount);
        return this;
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        int byteCount = byteBuffer.remaining();
        int byteLength = byteCount;
        while (byteLength > 0) {
            Segment segment = commonWritableSegment(1);
            byteLength -= segment.write(byteBuffer);
        }
        this.size += byteCount;
        return byteCount;
    }

    @Override
    public IBuffer writeAll(ISource source) throws IOException {
        for (; ; ) {
            if (source.read(this, Segment.SIZE) == -1L) {
                break;
            }
        }
        return this;
    }

    @Override
    public IBuffer write(byte[] bytes) throws IOException {
        return write(bytes, 0, bytes.length);
    }

    @Override
    public IBuffer write(byte[] bytes, int offset, int byteCount) throws IOException {
        int writeOffset = 0;
        while (byteCount - writeOffset > 0) {
            Segment segment = commonWritableSegment(1);
            writeOffset += segment.write(bytes, offset + writeOffset, byteCount - writeOffset);
        }
        this.size += writeOffset;
        return this;
    }

    @Override
    public IBuffer write(IByteString byteString) throws IOException {
        byteString.write(this);
        return this;
    }

    @Override
    public IBuffer write(IByteString byteString, int offset, int length) throws IOException {
        byteString.write(this, offset, length);
        return this;
    }

    @Override
    public IBuffer writeByte(int b) throws IOException {
        Segment segment = commonWritableSegment(1);
        segment.bytes[segment.limit++] = (byte) (b & 0xff);
        this.size += 1;
        return this;
    }

    @Override
    public IBuffer writeShort(int s) throws IOException {
        Segment segment = commonWritableSegment(2);
        segment.bytes[segment.limit++] = (byte) ((s >> 8) & 0xff);
        segment.bytes[segment.limit++] = (byte) (s & 0xff);
        this.size += 2;
        return this;
    }

    @Override
    public IBuffer writeInt(int i) throws IOException {
        Segment segment = commonWritableSegment(4);
        segment.bytes[segment.limit++] = (byte) ((i >> 24) & 0xff);
        segment.bytes[segment.limit++] = (byte) ((i >> 16) & 0xff);
        segment.bytes[segment.limit++] = (byte) ((i >> 8) & 0xff);
        segment.bytes[segment.limit++] = (byte) (i & 0xff);
        this.size += 4;
        return this;
    }

    @Override
    public IBuffer writeLong(long l) throws IOException {
        Segment segment = commonWritableSegment(8);
        segment.bytes[segment.limit++] = (byte) ((l >> 56) & 0xff);
        segment.bytes[segment.limit++] = (byte) ((l >> 48) & 0xff);
        segment.bytes[segment.limit++] = (byte) ((l >> 40) & 0xff);
        segment.bytes[segment.limit++] = (byte) ((l >> 32) & 0xff);
        segment.bytes[segment.limit++] = (byte) ((l >> 24) & 0xff);
        segment.bytes[segment.limit++] = (byte) ((l >> 16) & 0xff);
        segment.bytes[segment.limit++] = (byte) ((l >> 8) & 0xff);
        segment.bytes[segment.limit++] = (byte) (l & 0xff);
        this.size += 8;
        return this;
    }

    @Override
    public IBuffer skip(int length) throws IOException {
        while (length > 0) {
            Segment segment = commonReadableSegment();
            if (ObjectUtils.isNull(segment)) {
                throw new EOFException();
            }
            int skip = segment.skip(Math.min(length, segment.length()));
            length -= skip;
            this.size -= skip;
            require(segment);
        }
        return this;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public OutputStream outputStream() {
        return new BufferedSinkOutputStream(this);
    }

    @Override
    public InputStream inputStream() {
        return new BufferedSourceInputStream(this);
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    private Segment commonWritableSegment(int minimumCapacity) {
        return segments.commonWritableSegment(minimumCapacity);
    }

    private Segment commonReadableSegment() {
        return segments.commonReadableSegment();
    }

    private void require(Segment segment) {
        segments.require(segment);
    }

}
