package org.jzl.io.impl;

import org.jetbrains.annotations.NotNull;
import org.jzl.io.IBufferedSink;

import java.io.IOException;
import java.io.OutputStream;

class BufferedSinkOutputStream extends OutputStream {

    private final IBufferedSink bufferedSink;

    BufferedSinkOutputStream(IBufferedSink bufferedSink) {
        this.bufferedSink = bufferedSink;
    }

    @Override
    public void write(int i) throws IOException {
        bufferedSink.writeByte((byte) (i & 0xff));
    }

    @Override
    public void write(@NotNull byte[] bytes, int offset, int length) throws IOException {
        bufferedSink.write(bytes, offset, length);
    }

    @Override
    public void write(@NotNull byte[] bytes) throws IOException {
        bufferedSink.write(bytes);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        bufferedSink.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
        bufferedSink.close();
    }
}
