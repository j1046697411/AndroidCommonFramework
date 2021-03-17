package org.jzl.android.mvvm.core;

import java.util.Set;

public interface IViewModelStore {

    IViewModel get(String key);

    void put(String key, IViewModel viewModel);

    IViewModel remove(String key);

    Set<String> getKeys();

    void clear();
}
