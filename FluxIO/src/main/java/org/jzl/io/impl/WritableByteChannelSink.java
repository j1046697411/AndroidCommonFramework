package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.ISegments;
import org.jzl.io.ISink;
import org.jzl.lang.util.ObjectUtils;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public class WritableByteChannelSink implements ISink {

    private final WritableByteChannel writableByteChannel;

    public WritableByteChannelSink(WritableByteChannel writableByteChannel) {
        this.writableByteChannel = writableByteChannel;
    }

    @Override
    public ISink write(IBuffer buffer, int byteCount) throws IOException {
        ISegments segments = buffer.segments();
        while (byteCount > 0) {
            Segment segment = segments.commonReadableSegment();
            if (ObjectUtils.isNull(segment) || segment.length() == 0) {
                break;
            }
            int writeLength = segment.read(writableByteChannel, byteCount);
            System.out.println(writeLength);
            segments.require(segment);
            if (writeLength == -1) {
                break;
            }
            byteCount -= writeLength;
            segments.setSize(buffer.size() - writeLength);
        }
        return this;
    }

    @Override
    public void close() throws IOException {
        writableByteChannel.close();
    }

    @Override
    public void flush() throws IOException {
    }
}
