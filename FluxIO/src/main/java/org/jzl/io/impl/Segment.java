package org.jzl.io.impl;

import org.jzl.io.IBufferedSink;
import org.jzl.io.IBufferedSource;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class Segment {

    public static final int SIZE = 8192;
    public static final int SHARE_MINIMUM = 1024;

    final byte[] bytes;
    int position;
    int limit;

    Segment next;
    Segment prev;

    boolean shared = false;

    Segment() {
        this.bytes = new byte[SIZE];
        this.next = this;
        this.prev = this;
    }

    Segment(byte[] bytes, int position, int limit, boolean shared) {
        this.bytes = bytes;
        this.position = position;
        this.limit = limit;
        this.shared = shared;
    }

    public int length() {
        return limit - position;
    }

    public int remaining() {
        return SIZE - limit;
    }

    void before(Segment segment) {
        segment.next = this;
        segment.prev = this.prev;

        segment.prev.next = segment;
        this.prev = segment;
    }

    void after(Segment segment) {
        segment.prev = this;
        segment.next = this.next;

        this.next.prev = segment;
        this.next = segment;
    }

    Segment copySegment() {
        this.shared = true;
        return new Segment(this.bytes, this.position, this.limit, true);
    }

    public boolean hasNext() {
        return this.next != this;
    }

    Segment pop() {
        this.next.prev = this.prev;
        this.prev.next = this.next;
        if (this.next == this || this.prev == this) {
            return null;
        }
        return this.next;
    }

    void reset() {
        this.prev = this;
        this.next = this;
        this.position = 0;
        this.limit = 0;
    }

    int write(ReadableByteChannel channel, int length) throws IOException{
        int writeLength = channel.read(ByteBuffer.wrap(this.bytes, limit, Math.min(length, remaining())));
        if (writeLength > 0){
            this.limit += writeLength;
        }
        return writeLength;
    }

    int write(byte[] bytes, int offset, int length) {
        int copyLength = Math.min(length, this.remaining());
        System.arraycopy(bytes, offset, this.bytes, limit, copyLength);
        this.limit += copyLength;
        return copyLength;
    }

    int write(ByteBuffer byteBuffer) {
        int length = Math.min(byteBuffer.limit() - byteBuffer.position(), remaining());
        byteBuffer.get(bytes, limit, length);
        this.limit += length;
        return length;
    }

    int write(InputStream inputStream, int length) throws IOException {
        int readLength = inputStream.read(this.bytes, this.limit, Math.min(length, remaining()));
        if (readLength != -1) {
            this.limit += readLength;
        }
        return readLength;
    }

    int write(IBufferedSource source, int length) throws IOException {
        int readLength = source.read(this.bytes, limit, Math.min(length, remaining()));
        this.limit += readLength;
        return readLength;
    }

    int write(Segment segment, int length) {
        return write(segment.bytes, segment.limit, Math.min(length, segment.length()));
    }

    int read(WritableByteChannel channel, int length) throws IOException{
        int readLength = channel.write(ByteBuffer.wrap(this.bytes, position, Math.min(length, length)));
        if (readLength != -1){
            this.position += readLength;
        }
        return readLength;
    }

    int read(OutputStream outputStream, int length) throws IOException {
        int writeLength = Math.min(length, length());
        outputStream.write(bytes, position, writeLength);
        this.position += writeLength;
        return writeLength;
    }

    int read(ByteBuffer byteBuffer) {
        int writeLength = Math.min(byteBuffer.remaining(), length());
        if (writeLength == 0) {
            return -1;
        }
        byteBuffer.put(this.bytes, this.position, writeLength);
        this.position += writeLength;
        return writeLength;
    }

    int read(byte[] bytes, int offset, int byteCount) {
        int length = Math.min(byteCount, length());
        System.arraycopy(this.bytes, this.position, bytes, offset, length);
        this.position += length;
        return length;
    }

    int read(IBufferedSink sink, int byteCount) throws IOException {
        int length = Math.min(length(), byteCount);
        sink.write(bytes, position, length);
        this.position += length;
        return length;
    }

    int read(Segment segment, int length) throws IOException {
        return read(segment.bytes, segment.limit, Math.min(length, segment.remaining()));
    }

    int indexOf(byte b) {
        for (int i = this.position, size = this.limit; i < size; i++) {
            if (this.bytes[i] == b) {
                return i - position;
            }
        }
        return -1;
    }

    int skip(int length) throws IOException {
        if (length > length()) {
            throw new EOFException();
        }
        this.position += length;
        return length;
    }

    ByteBuffer asByteBuffer() {
        this.shared = true;
        return ByteBuffer.wrap(this.bytes, position, length()).asReadOnlyBuffer();
    }

    ByteBuffer asByteBuffer(int length) {
        this.shared = true;
        return ByteBuffer.wrap(this.bytes, position, Math.min(length, length())).asReadOnlyBuffer();
    }

    boolean isShared() {
        return shared;
    }

    public static Segment of() {
        return new Segment();
    }

}
