package org.jzl.router.interceptor;

import org.jzl.router.INavigator;
import org.jzl.router.IService;
import org.jzl.router.Postcard;

public interface Interceptor extends IService {

    default int getPriority(){
        return 5;
    }

    boolean checkIntercept(Postcard postcard);

    Object intercept(INavigator navigator, Postcard postcard, PostcardChain chain);

}