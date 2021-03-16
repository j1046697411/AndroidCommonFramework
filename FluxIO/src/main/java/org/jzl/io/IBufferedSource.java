package org.jzl.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

public interface IBufferedSource extends ISource, ReadableByteChannel {

    IBuffer buffer();

    boolean exhausted();

    @Override
    int read(IBuffer buffer, int byteCount) throws IOException;

    int readAll(ISink sink) throws IOException;

    int read(byte[] bytes) throws IOException;

    int read(byte[] bytes, int offset, int byteCount) throws IOException;

    int readFully(byte[] bytes) throws IOException;

    int readFully(byte[] bytes, int offset, int byteCount) throws IOException;

    byte readByte() throws IOException;

    short readShort() throws IOException;

    int readInt() throws IOException;

    long readLong() throws IOException;

    byte[] readByteArray(int byteCount) throws IOException;

    byte[] readByteArray() throws IOException;

    ByteBuffer readByteBuffer() throws IOException;

    ByteBuffer readByteBuffer(int length) throws IOException;

    IByteString readByteString(int length) throws IOException;

    IByteString readByteString() throws IOException;

    String readString(Charset charset) throws IOException;

    String readString(int byteCount, Charset charset) throws IOException;

    IBufferedSource skip(int length) throws IOException;

    InputStream inputStream();
}
