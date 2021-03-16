package org.jzl.io;

import java.io.IOException;

public interface IPipeline extends ISink, ISource {

    @Override
    IPipeline write(IBuffer buffer, int byteCount) throws IOException;

    @Override
    int read(IBuffer buffer, int byteCount) throws IOException;

    @Override
    void flush() throws IOException;

    @Override
    void close() throws IOException;
}
