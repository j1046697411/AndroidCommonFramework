package org.jzl.router;

public interface IRouteHelper extends IRoutes {

    RouteMeta getRouteMeta(String path);

    RouteMeta getServiceRouteMeta(Class<?> serviceType);

    Postcard buildServicePostcard(RouteMeta routeMeta);

    <T> T buildService(Postcard postcard);

}
