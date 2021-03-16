package org.jzl.io.impl;

import org.jetbrains.annotations.NotNull;
import org.jzl.io.IBufferedSource;

import java.io.IOException;
import java.io.InputStream;

class BufferedSourceInputStream extends InputStream {

    final IBufferedSource source;

    BufferedSourceInputStream(IBufferedSource source) {
        this.source = source;
    }

    @Override
    public int read() throws IOException {
        return source.readByte();
    }

    @Override
    public int read(@NotNull byte[] bytes) throws IOException {
        return source.read(bytes);
    }

    @Override
    public int read(@NotNull byte[] bytes, int i, int i1) throws IOException {
        return source.read(bytes, i, i);
    }

    @Override
    public int available() throws IOException {
        return Math.min(source.buffer().size(), Integer.MAX_VALUE);
    }

    @Override
    public void close() throws IOException {
        super.close();
        source.close();
    }
}
