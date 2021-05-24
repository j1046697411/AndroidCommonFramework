package org.jzl.android.mvvm;

import androidx.annotation.Nullable;

/**
 * 向ViewDataBinding绑定变量提供的接口
 */
public interface IVariableBinder {

    void bindVariable(int variableId, @Nullable Object value);

}
