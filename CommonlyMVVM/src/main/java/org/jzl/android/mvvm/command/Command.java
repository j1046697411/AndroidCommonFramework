package org.jzl.android.mvvm.command;

import androidx.lifecycle.LiveData;

public abstract class Command<A extends Command.IAction> extends LiveData<A> {

    Command<A> action(A action) {
        setValue(action);
        return this;
    }

    public interface IAction {
    }
}
