package org.jzl.android.mvvm.core;

import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class ViewModelStore implements IViewModelStore {

    private final HashMap<String, IViewModel> viewModels = new HashMap<>();
    private final Set<String> unmodifiableKeys = Collections.unmodifiableSet(this.viewModels.keySet());

    public final IViewModel get(String key) {
        return viewModels.get(key);
    }

    public final void put(String key, IViewModel viewModel) {
        IViewModel oldViewModel = this.viewModels.put(key, viewModel);
        if (ObjectUtils.nonNull(oldViewModel)) {
            oldViewModel.unbind();
        }
    }

    @Override
    public IViewModel remove(String key) {
        return viewModels.remove(key);
    }

    public final Set<String> getKeys() {
        return unmodifiableKeys;
    }

    public void clear() {
        ForeachUtils.each(this.viewModels.values(), IViewModel::unbind);
        viewModels.clear();
    }
}
