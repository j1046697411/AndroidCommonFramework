package org.jzl.router.service;

import android.content.Context;

import org.jzl.router.INavigationCallback;
import org.jzl.router.INavigator;
import org.jzl.router.IService;
import org.jzl.router.Postcard;

public interface IInterceptorService extends IService {

    Object doInterceptions(Context context, INavigator navigator, Postcard postcard, INavigationCallback navigationCallback);

}
