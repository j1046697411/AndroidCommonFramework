package org.jzl.android.mvvm.command;


import org.jzl.android.mvvm.command.action.Action;

class CommandImpl extends AbstractCommand {

    private final Action action;

    public CommandImpl(Action action) {
        this.action = action;
    }

    @Override
    protected boolean executeAction() {
        return action.execute();
    }
}
