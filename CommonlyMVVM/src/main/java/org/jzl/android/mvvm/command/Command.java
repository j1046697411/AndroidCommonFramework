package org.jzl.android.mvvm.command;


import org.jzl.android.mvvm.command.action.Action;
import org.jzl.android.mvvm.command.action.BooleanAction;
import org.jzl.android.mvvm.command.action.ObjectAction;
import org.jzl.android.mvvm.command.interceptor.CommandInterceptor;
import org.jzl.android.mvvm.command.interceptor.IntervalInterceptor;

public interface Command extends Action {

    Command addInterceptor(CommandInterceptor interceptor);

    default Command interval(long interval) {
        return addInterceptor(new IntervalInterceptor(interval));
    }

    static Command of(Action action) {
        return new CommandImpl(action);
    }

    static <T> ReplyCommand<T> ofObject(ObjectAction<T> action) {
        return new ReplyCommandImpl<>(action);
    }

    static BooleanReplyCommand ofBoolean(BooleanAction action) {
        return new BooleanReplyCommandImpl(action);
    }

}
