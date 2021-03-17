package org.jzl.router;

import android.os.Bundle;

public interface ISyringe<T> {
    void inject(T target, Bundle extras);
}
