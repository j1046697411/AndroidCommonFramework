package org.jzl.android.mvvm.core;

public interface IKeyGenerator {

    String generate(String parentKey, IExtendView<?, ?, ?> extendView, Class<?> viewModelType);

}
