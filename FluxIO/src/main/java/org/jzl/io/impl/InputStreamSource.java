package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.ISegments;
import org.jzl.io.ISource;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamSource implements ISource {

    private final InputStream inputStream;

    public InputStreamSource(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read(IBuffer buffer, int byteCount) throws IOException {
        ISegments segments = buffer.segments();
        Segment segment = segments.commonWritableSegment(1);
        int length = segment.write(inputStream, byteCount);
        if (length != -1) {
            segments.setSize(length + buffer.size());
        }
        return length;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
