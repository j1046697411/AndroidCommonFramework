package org.jzl.router.interceptor;

import android.content.Context;

import org.jzl.lang.util.ObjectUtils;
import org.jzl.router.INavigationCallback;
import org.jzl.router.INavigator;
import org.jzl.router.Postcard;
import org.jzl.router.service.IInterceptorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class InterceptorServiceImpl implements IInterceptorService {

    private final List<Interceptor> interceptors = new ArrayList<>();

    @Override
    public void init(Context context) {
        Collections.sort(this.interceptors, (o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority()));
    }

    @Override
    public Object doInterceptions(Context context, INavigator navigator, Postcard postcard, INavigationCallback navigationCallback) {
        Iterator<Interceptor> interceptors = this.interceptors.iterator();
        PostcardChain chain = new PostcardChain() {
            @Override
            public Object chain(INavigator navigator, Postcard postcard) {
                return doInterceptions(interceptors, this, context, navigator, postcard, navigationCallback);
            }

            @Override
            public void onInterrupt(Postcard postcard, Throwable throwable) {
                if (ObjectUtils.nonNull(navigationCallback)) {
                    navigationCallback.onInterrupt(postcard, throwable);
                }
            }
        };
        return doInterceptions(interceptors, chain, context, navigator, postcard, navigationCallback);
    }

    private Object doInterceptions(Iterator<Interceptor> iterator, PostcardChain chain, Context context, INavigator navigator, Postcard postcard, INavigationCallback navigationCallback) {
        while (iterator.hasNext()) {
            Interceptor interceptor = iterator.next();
            if (interceptor.checkIntercept(postcard)) {
                return interceptor.intercept(navigator, postcard, chain);
            }
        }
        return navigator.navigation(context, navigator, postcard, navigationCallback);
    }

}
