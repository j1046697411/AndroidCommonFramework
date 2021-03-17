package org.jzl.router.service;

import android.content.Context;

import org.jzl.router.INavigationCallback;
import org.jzl.router.IService;
import org.jzl.router.Postcard;
import org.jzl.router.RouteMeta;

public interface IActivityStartService extends IService {

    Object startActivity(Context context, Postcard postcard, RouteMeta routeMeta, INavigationCallback navigationCallback);

}
