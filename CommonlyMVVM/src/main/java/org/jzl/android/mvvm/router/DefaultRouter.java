package org.jzl.android.mvvm.router;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import org.jzl.android.mvvm.IExtendView;

import java.nio.ByteBuffer;

public class DefaultRouter implements Router {

    private final IExtendView<?, ?, ?> extendView;

    public DefaultRouter(@NonNull IExtendView<?, ?, ?> extendView) {
        this.extendView = extendView;
    }

    @Override
    public void injection(Object injection) {
        ARouter.getInstance().inject(injection);
    }

    @Override
    public Postcard build(String path) {
        return ARouter.getInstance().build(path);
    }

    @Override
    public Postcard build(Uri uri) {
        return ARouter.getInstance().build(uri);
    }

    @Override
    public <T> T navigation(Class<?  extends T> service){
        return ARouter.getInstance().navigation(service);
    }

    @Override
    public Object navigation(Postcard postcard) {
        return postcard.navigation(extendView.getCurrentActivity());
    }

    @Override
    public Object navigation(Postcard postcard, NavigationCallback navigationCallback) {
        return postcard.navigation(extendView.getCurrentActivity(), navigationCallback);
    }

    @Override
    public void navigation(Postcard postcard, int requestCode, NavigationCallback navigationCallback) {
        postcard.navigation(extendView.getCurrentActivity(), requestCode, navigationCallback);
    }
}
