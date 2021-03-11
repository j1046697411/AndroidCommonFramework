package org.jzl.android.mvvm.command.interceptor;


import org.jzl.android.mvvm.command.Command;

public class IntervalInterceptor implements CommandInterceptor {

    private long lastExecuteTime;
    private final long interval;

    public IntervalInterceptor(long interval) {
        this.interval = interval;
    }

    @Override
    public boolean intercept(Command command) {
        return System.currentTimeMillis() - lastExecuteTime <= interval;
    }

    @Override
    public void onContinue(Command command, boolean result) {
        lastExecuteTime = System.currentTimeMillis();
    }
}
