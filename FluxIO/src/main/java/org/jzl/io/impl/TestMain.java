package org.jzl.io.impl;


import org.jzl.io.FluxIO;
import org.jzl.io.IBufferedSource;
import org.jzl.io.ISink;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

public class TestMain {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\jzl\\Desktop\\testName\\test3.png");
        File readFile = new File("C:\\Users\\jzl\\Desktop\\testName\\test2.png");
        IBufferedSource source = FluxIO.buffer(FluxIO.source(FileChannel.open(readFile.toPath(), StandardOpenOption.READ)));
        ISink sink = FluxIO.sink(FileChannel.open( file.toPath(),  StandardOpenOption.CREATE, StandardOpenOption.WRITE));
        System.out.println(source.readAll(sink));
        source.close();
        sink.flush();
        sink.close();
    }
}
