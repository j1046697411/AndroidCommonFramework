package org.jzl.android.mvvm.core;

public interface IViewModelBindVariableOwner<VM  extends IViewModel & IViewModelBindVariableOwner<VM>> {

    Object getBindVariable();

}
