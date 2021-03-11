package org.jzl.android.mvvm.command;


import org.jzl.android.mvvm.command.interceptor.CommandInterceptor;
import org.jzl.android.mvvm.command.interceptor.IntervalInterceptor;

public interface ReplyCommand<T> extends Command {

    ReplyCommand<T> reply(T result);

    @Override
    ReplyCommand<T> addInterceptor(CommandInterceptor interceptor);

    @Override
    default ReplyCommand<T> interval(long interval) {
        return addInterceptor(new IntervalInterceptor(interval));
    }
}
