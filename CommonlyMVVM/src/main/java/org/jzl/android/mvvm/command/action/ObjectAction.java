package org.jzl.android.mvvm.command.action;

public interface ObjectAction<T> {

    boolean execute(T result);

}
