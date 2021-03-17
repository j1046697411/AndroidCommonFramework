package org.jzl.router.routes;

import org.jzl.router.IRouteGroup;
import org.jzl.router.IRoutes;
import org.jzl.router.RouteMeta;
import org.jzl.router.RouteType;
import org.jzl.router.core.ActivityStartServiceImpl;
import org.jzl.router.core.NavigationServiceImpl;
import org.jzl.router.core.ObjectBuildServiceImpl;
import org.jzl.router.core.PostcardServiceImpl;
import org.jzl.router.core.RouteMetaServiceImpl;
import org.jzl.router.interceptor.InterceptorServiceImpl;
import org.jzl.router.service.IActivityStartService;
import org.jzl.router.service.IInterceptorService;
import org.jzl.router.service.INavigationService;
import org.jzl.router.service.IObjectBuildService;
import org.jzl.router.service.IPostcardService;
import org.jzl.router.service.IRouteMetaService;

public class JRouter$$Services$$app implements IRouteGroup {

    @Override
    public void loadInto(IRoutes routes) {
        routes.service(INavigationService.class, RouteMeta.build("", "service", RouteType.SERVICE, NavigationServiceImpl.class));
        routes.service(IInterceptorService.class, RouteMeta.build("", "service", RouteType.SERVICE, InterceptorServiceImpl.class));
        routes.service(IPostcardService.class, RouteMeta.build("", "", RouteType.SERVICE, PostcardServiceImpl.class));
        routes.service(IActivityStartService.class, RouteMeta.build("", "", RouteType.SERVICE, ActivityStartServiceImpl.class));
        routes.service(IObjectBuildService.class, RouteMeta.build("", "", RouteType.SERVICE, ObjectBuildServiceImpl.class));
        routes.service(IRouteMetaService.class, RouteMeta.build("", "", RouteType.SERVICE, RouteMetaServiceImpl.class));
    }
}
