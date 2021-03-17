package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.ISink;

import java.io.IOException;

public class ForwardingSink implements ISink {

    private final ISink delegate;

    public ForwardingSink(ISink delegate) {
        this.delegate = delegate;
    }

    @Override
    public ISink write(IBuffer buffer, int byteCount) throws IOException {
        delegate.write(buffer, byteCount);
        return this;
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }

    @Override
    public void flush() throws IOException {
        delegate.flush();
    }

    public ISink getDelegate() {
        return delegate;
    }
}
