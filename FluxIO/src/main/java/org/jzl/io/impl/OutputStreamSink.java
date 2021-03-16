package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.ISegments;
import org.jzl.io.ISink;
import org.jzl.lang.util.ObjectUtils;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamSink implements ISink {

    private final OutputStream outputStream;

    public OutputStreamSink(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public ISink write(IBuffer buffer, int byteCount) throws IOException {
        int length = Math.min(byteCount, buffer.size());
        ISegments segments = buffer.segments();
        while (length > 0) {
            Segment segment = segments.commonReadableSegment();
            if (ObjectUtils.isNull(segment) || segment.length() == 0) {
                break;
            }
            int readLength = segment.read(outputStream, length);
            if (readLength == -1) {
                break;
            }
            length -= readLength;
            segments.setSize(buffer.size() - readLength);
            segments.require(segment);
        }
        return this;
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }
}
