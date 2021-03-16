package org.jzl.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.WritableByteChannel;

public interface IBufferedSink extends ISink, WritableByteChannel {

    IBuffer buffer();

    IBufferedSink writeAll(ISource source) throws IOException;

    @Override
    IBufferedSink write(IBuffer buffer, int byteCount) throws IOException;

    IBufferedSink write(byte[] bytes) throws IOException;

    IBufferedSink write(byte[] bytes, int offset, int byteCount) throws IOException;

    IBufferedSink write(IByteString byteString) throws IOException;

    IBufferedSink write(IByteString byteString, int offset, int length) throws IOException;

    IBufferedSink writeByte(int b) throws IOException;

    IBufferedSink writeShort(int s) throws IOException;

    IBufferedSink writeInt(int i) throws IOException;

    IBufferedSink writeLong(long l) throws IOException;

    OutputStream outputStream();
}
