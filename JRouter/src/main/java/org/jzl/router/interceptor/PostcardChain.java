package org.jzl.router.interceptor;

import org.jzl.router.INavigator;
import org.jzl.router.Postcard;

public interface PostcardChain {

    Object chain(INavigator navigator, Postcard postcard);

    void onInterrupt(Postcard postcard, Throwable throwable);

}
