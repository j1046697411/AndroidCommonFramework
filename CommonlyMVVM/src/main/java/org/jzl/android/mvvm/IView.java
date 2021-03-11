package org.jzl.android.mvvm;

import android.net.Uri;

import androidx.core.util.Consumer;

import com.alibaba.android.arouter.facade.Postcard;

import org.jzl.android.mvvm.router.Router;
import org.jzl.lang.util.ObjectUtils;


public interface IView {

    Router router();

    void showToast(String text);

    void bindVariable(int variableId, Object value);

    default <T> T getProviderService(Class<? extends T> type) {
        return router().navigation(type);
    }

    default void navigation(String url) {
        navigation(url, null);
    }

    default void navigation(String url, int requestCode, Consumer<Postcard> consumer) {
        Postcard postcard = router().build(url);
        if (ObjectUtils.nonNull(consumer)) {
            consumer.accept(postcard);
        }
        router().navigation(postcard, requestCode, null);
    }

    default void navigation(String url, Consumer<Postcard> consumer) {
        navigation(url, -1, consumer);
    }

    default void navigation(Uri uri, int requestCode, Consumer<Postcard> consumer) {
        Postcard postcard = router().build(uri);
        if (ObjectUtils.nonNull(consumer)) {
            consumer.accept(postcard);
        }
        router().navigation(postcard, requestCode, null);
    }

    default void navigation(Uri uri, Consumer<Postcard> consumer) {
        navigation(uri, -1, consumer);
    }

    default void navigation(Uri uri) {
        navigation(uri, null);
    }
}
