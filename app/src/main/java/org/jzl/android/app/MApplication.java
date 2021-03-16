package org.jzl.android.app;

import android.app.Application;

import org.jzl.android.mvvm.M;
import org.jzl.android.recyclerview.util.Logger;
import org.jzl.io.FluxIO;
import org.jzl.io.IBufferedSink;
import org.jzl.io.IBufferedSource;
import org.jzl.lang.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MApplication extends Application {

    private static final Logger log = Logger.logger(MApplication.class);

    @Override
    public void onCreate() {
        super.onCreate();
        M.initialise(this);
        ByteArrayInputStream inputStream = new ByteArrayInputStream("11111111111111111111111".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (IBufferedSource source = FluxIO.buffer(FluxIO.source(inputStream)); IBufferedSink sink = FluxIO.buffer(FluxIO.sink(outputStream))) {
            sink.writeAll(source);
            sink.flush();
            byte[] bytes = outputStream.toByteArray();
            log.i(StringUtils.toHexString(":", bytes, true));
            log.i(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
