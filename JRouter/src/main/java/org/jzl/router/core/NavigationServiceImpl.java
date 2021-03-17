package org.jzl.router.core;

import android.content.Context;

import org.jzl.router.INavigationCallback;
import org.jzl.router.INavigator;
import org.jzl.router.IService;
import org.jzl.router.Postcard;
import org.jzl.router.RouteMeta;
import org.jzl.router.service.IActivityStartService;
import org.jzl.router.service.INavigationService;
import org.jzl.router.service.IObjectBuildService;

public class NavigationServiceImpl implements INavigationService {

    private IActivityStartService activityStartService;
    private IObjectBuildService objectBuildService;

    @Override
    public void init(Context context) {
        this.activityStartService = JRouter.getInstance().navigation(IActivityStartService.class);
        this.objectBuildService = JRouter.getInstance().navigation(IObjectBuildService.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object navigation(Context context, INavigator navigator, Postcard postcard, INavigationCallback navigationCallback) {
        RouteMeta routeMeta = postcard.getRouteMeta();
        switch (routeMeta.getRouteType()) {
            case ACTIVITY: {
                return startActivity(context, postcard, routeMeta, navigationCallback);
            }
            case BROADCAST_RECEIVERS:
            case CONTENT_PROVIDER:
            case FRAGMENT: {
                Object target = objectBuildService.buildObject(context, postcard);
//                if (target instanceof Fragment) {
//                    ((Fragment) target).setArguments(postcard.getExtras());
//                } else if (target instanceof androidx.fragment.app.Fragment) {
//                    ((androidx.fragment.app.Fragment) target).setArguments(postcard.getExtras());
//                } else if (target instanceof IParameterizable) {
//                    ((IParameterizable) target).setArguments(postcard.getExtras());
//                }
                return target;
            }
            case SERVICE: {
                return JRouter.getInstance().navigation((Class<? extends IService>) routeMeta.getTargetClass());
            }
            case ANDROID_SERVICE: {
            }
        }
        return null;
    }

    private Object startActivity(Context context, Postcard postcard, RouteMeta routeMeta, INavigationCallback navigationCallback) {
        return activityStartService.startActivity(context, postcard, routeMeta, navigationCallback);
    }

}
