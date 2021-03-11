package org.jzl.android.mvvm.command.action;

@FunctionalInterface
public interface BooleanAction {

    boolean execute(boolean result);

}
