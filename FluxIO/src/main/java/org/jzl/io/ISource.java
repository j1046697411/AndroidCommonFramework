package org.jzl.io;

import java.io.Closeable;
import java.io.IOException;

public interface ISource extends Closeable {

    int read(IBuffer buffer, int byteCount) throws IOException;

    @Override
    void close() throws IOException;

}
