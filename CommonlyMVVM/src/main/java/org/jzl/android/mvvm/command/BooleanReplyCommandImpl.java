package org.jzl.android.mvvm.command;


import org.jzl.android.mvvm.command.action.BooleanAction;
import org.jzl.android.mvvm.command.interceptor.CommandInterceptor;

class BooleanReplyCommandImpl extends AbstractCommand implements BooleanReplyCommand {

    private boolean result;
    private final BooleanAction action;

    public BooleanReplyCommandImpl(BooleanAction action) {
        this.action = action;
    }

    @Override
    protected boolean executeAction() {
        return action.execute(result);
    }

    @Override
    public BooleanReplyCommand reply(boolean result) {
        this.result = result;
        return this;
    }

    @Override
    public BooleanReplyCommand addInterceptor(CommandInterceptor interceptor) {
        super.addInterceptor(interceptor);
        return this;
    }
}
