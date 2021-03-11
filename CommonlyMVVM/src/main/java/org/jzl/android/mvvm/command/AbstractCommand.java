package org.jzl.android.mvvm.command;

import org.jzl.android.mvvm.command.interceptor.CommandInterceptor;
import org.jzl.lang.util.ObjectUtils;

import java.util.concurrent.CopyOnWriteArrayList;


public abstract class AbstractCommand implements Command {

    private final CopyOnWriteArrayList<CommandInterceptor> interceptors = new CopyOnWriteArrayList<>();

    @Override
    public Command addInterceptor(CommandInterceptor interceptor) {
        if (ObjectUtils.nonNull(interceptor)) {
            this.interceptors.add(interceptor);
        }
        return this;
    }

    @Override
    public final boolean execute() {
        if (intercept()) {
            return false;
        }
        boolean result = executeAction();
        onContinue(result);
        return result;
    }

    protected boolean intercept() {
        for (CommandInterceptor interceptor : interceptors) {
            if (interceptor.intercept(this)) {
                return true;
            }
        }
        return false;
    }

    protected void onContinue(boolean result) {
        for (CommandInterceptor interceptor : interceptors) {
            interceptor.onContinue(this, result);
        }
    }

    protected abstract boolean executeAction();

}
