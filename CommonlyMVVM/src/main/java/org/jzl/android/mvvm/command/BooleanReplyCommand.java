package org.jzl.android.mvvm.command;


import org.jzl.android.mvvm.command.interceptor.CommandInterceptor;
import org.jzl.android.mvvm.command.interceptor.IntervalInterceptor;

public interface BooleanReplyCommand extends Command {

    BooleanReplyCommand reply(boolean result);

    @Override
    BooleanReplyCommand addInterceptor(CommandInterceptor interceptor);

    @Override
    default BooleanReplyCommand interval(long interval) {
        return addInterceptor(new IntervalInterceptor(interval));
    }
}
