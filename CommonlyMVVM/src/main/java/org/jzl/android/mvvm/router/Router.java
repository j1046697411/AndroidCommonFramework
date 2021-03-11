package org.jzl.android.mvvm.router;

import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

public interface Router {

    void injection(Object injection);

    Postcard build(String path);

    Postcard build(Uri uri);

    <T> T navigation(Class<? extends T> service);

    Object navigation(Postcard postcard);

    Object navigation(Postcard postcard, NavigationCallback navigationCallback);

    void navigation(Postcard postcard, int requestCode, NavigationCallback navigationCallback);
}
