package org.jzl.android.mvvm.command.interceptor;


import org.jzl.android.mvvm.command.Command;

public interface CommandInterceptor {

    boolean intercept(Command command);

    void onContinue(Command command, boolean result);

}
