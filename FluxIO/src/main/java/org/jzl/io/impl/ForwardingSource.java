package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.ISource;

import java.io.IOException;

public class ForwardingSource implements ISource {

    private final ISource delegate;

    public ForwardingSource(ISource delegate) {
        this.delegate = delegate;
    }

    @Override
    public int read(IBuffer buffer, int byteCount) throws IOException {
        return delegate.read(buffer, byteCount);
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }

    public ISource getDelegate() {
        return delegate;
    }
}
