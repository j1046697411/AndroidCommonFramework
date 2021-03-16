package org.jzl.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public interface ISink extends Closeable, Flushable {

    ISink write(IBuffer buffer, int byteCount) throws IOException;

}
