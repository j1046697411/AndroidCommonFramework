package org.jzl.android.mvvm.command;


import org.jzl.android.mvvm.command.action.ObjectAction;
import org.jzl.android.mvvm.command.interceptor.CommandInterceptor;

class ReplyCommandImpl<T> extends AbstractCommand implements ReplyCommand<T> {

    private final ObjectAction<T> action;
    private T result;

    ReplyCommandImpl(ObjectAction<T> action) {
        this.action = action;
    }

    @Override
    public ReplyCommand<T> reply(T result) {
        this.result = result;
        return this;
    }

    @Override
    protected boolean executeAction() {
        return action.execute(result);
    }

    @Override
    public ReplyCommand<T> addInterceptor(CommandInterceptor interceptor) {
        super.addInterceptor(interceptor);
        return this;
    }

}
