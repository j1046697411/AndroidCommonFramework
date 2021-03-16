package org.jzl.io;

import org.jzl.io.impl.InputStreamSource;
import org.jzl.io.impl.OutputStreamSink;
import org.jzl.io.impl.ReadableByteChannelSource;
import org.jzl.io.impl.RealBufferedSink;
import org.jzl.io.impl.RealBufferedSource;
import org.jzl.io.impl.WritableByteChannelSink;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class FluxIO {

    public static ISource source(ReadableByteChannel channel){
        return new ReadableByteChannelSource(channel);
    }

    public static ISource source(InputStream inputStream) {
        return new InputStreamSource(inputStream);
    }

    public static ISource source(File file) throws IOException {
        return source(new FileInputStream(file));
    }

    public static ISource source(Socket socket) throws IOException {
        return source(socket.getInputStream());
    }

    public static ISink sink(OutputStream outputStream) {
        return new OutputStreamSink(outputStream);
    }

    public static ISink sink(File file) throws IOException {
        return sink(new FileOutputStream(file));
    }

    public static ISink sink(WritableByteChannel channel){
        return new WritableByteChannelSink(channel);
    }

    public static ISink sink(Socket socket) throws IOException {
        return sink(socket.getOutputStream());
    }

    public static IBufferedSink buffer(ISink sink) {
        return new RealBufferedSink(sink);
    }

    public static IBufferedSource buffer(ISource source) {
        return new RealBufferedSource(source);
    }
}
