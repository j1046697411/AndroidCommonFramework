package org.jzl.android.recyclerview.manager.layout;

import org.jzl.lang.util.ForeachUtils;
import org.jzl.lang.util.ObjectUtils;

import java.util.concurrent.CopyOnWriteArrayList;

public class CompositeEmptyLayoutCallback implements EmptyLayoutManager.EmptyLayoutCallback {

    private final CopyOnWriteArrayList<EmptyLayoutManager.EmptyLayoutCallback> emptyLayoutCallbacks = new CopyOnWriteArrayList<>();

    public void addEmptyLayoutCallback(EmptyLayoutManager.EmptyLayoutCallback emptyLayoutCallback) {
        if (ObjectUtils.nonNull(emptyLayoutCallback)) {
            this.emptyLayoutCallbacks.add(emptyLayoutCallback);
        }
    }

    public void removeEmptyLayoutCallback(EmptyLayoutManager.EmptyLayoutCallback emptyLayoutCallback) {
        this.emptyLayoutCallbacks.remove(emptyLayoutCallback);
    }

    @Override
    public void onEmptyLayoutStateChanged(boolean isEmpty) {
        ForeachUtils.each(emptyLayoutCallbacks, target -> target.onEmptyLayoutStateChanged(isEmpty));
    }
}
