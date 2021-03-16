package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.ISegments;
import org.jzl.io.ISource;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

public class ReadableByteChannelSource implements ISource {

    private final ReadableByteChannel channel;

    public ReadableByteChannelSource(ReadableByteChannel channel) {
        this.channel = channel;
    }

    @Override
    public int read(IBuffer buffer, int byteCount) throws IOException {
        ISegments segments = buffer.segments();
        Segment segment = segments.commonWritableSegment(1);
        int length = segment.write(channel, byteCount);
        if (length != -1) {
            segments.setSize(buffer.size() + length);
        }
        System.out.println("length -> " + length + "," + segment.position + "," + segment.limit + "," + Arrays.toString(segment.bytes));
        return length;
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }
}
