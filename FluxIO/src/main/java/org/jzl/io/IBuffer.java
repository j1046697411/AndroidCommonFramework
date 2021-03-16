package org.jzl.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;

public interface IBuffer extends IBufferedSink, IBufferedSource, ByteChannel, IPipeline {

    ISegments segments();

    int size();

    @Override
    int write(ByteBuffer byteBuffer) throws IOException;

    @Override
    IBufferedSink writeAll(ISource source) throws IOException;

    @Override
    IBuffer writeShort(int s) throws IOException;

    @Override
    IBuffer writeLong(long l) throws IOException;

    @Override
    IBuffer writeInt(int i) throws IOException;

    @Override
    IBuffer writeByte(int b) throws IOException;

    @Override
    IBuffer write(IByteString byteString, int offset, int length) throws IOException;

    @Override
    IBuffer write(IByteString byteString) throws IOException;

    @Override
    IBuffer write(byte[] bytes, int offset, int byteCount) throws IOException;

    @Override
    IBuffer write(byte[] bytes) throws IOException;

    @Override
    IBuffer write(IBuffer buffer, int byteCount) throws IOException;

    @Override
    int read(byte[] bytes, int offset, int byteCount) throws IOException;

    @Override
    int read(byte[] bytes) throws IOException;

    @Override
    String readString(int byteCount, Charset charset) throws IOException;

    @Override
    int read(IBuffer buffer, int byteCount) throws IOException;

    @Override
    byte readByte() throws IOException;

    @Override
    byte[] readByteArray() throws IOException;

    @Override
    byte[] readByteArray(int byteCount) throws IOException;

    @Override
    int readAll(ISink sink) throws IOException;

    @Override
    int readFully(byte[] bytes) throws IOException;

    @Override
    int readFully(byte[] bytes, int offset, int byteCount) throws IOException;

    @Override
    int readInt() throws IOException;

    @Override
    long readLong() throws IOException;

    @Override
    short readShort() throws IOException;

    @Override
    String readString(Charset charset) throws IOException;

    @Override
    int read(ByteBuffer byteBuffer) throws IOException;

    int indexOf(byte b) throws IOException;

    @Override
    IBuffer skip(int length) throws IOException;

    @Override
    InputStream inputStream();

    @Override
    OutputStream outputStream();
}
