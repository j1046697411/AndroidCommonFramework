package org.jzl.io.impl;


import org.jzl.lang.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class TestMain {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        File file = new File("C:\\Users\\jzl\\Desktop\\testName\\test_2.zip");
        File readFile = new File("C:\\Users\\jzl\\Desktop\\testName\\test.zip");
//        ISource source = FluxIO.source(readFile);
//        ISink sink = FluxIO.sink(file);
//        IBuffer buffer = new Buffer();
//
//        long start = System.currentTimeMillis();
//        while (source.read(buffer, Segment.SIZE) != -1){
//            sink.write(buffer, buffer.size());
//        }
//        sink.flush();
//        System.out.println((System.currentTimeMillis() - start) / 1000f );
//        source.close();
//        sink.close();
//        System.out.println(SegmentPool.POOL.getFreeCount());

        long start = System.currentTimeMillis();
        StreamUtils.copyFile(readFile, file);
        System.out.println((System.currentTimeMillis() - start) / 1000f );

    }
}
