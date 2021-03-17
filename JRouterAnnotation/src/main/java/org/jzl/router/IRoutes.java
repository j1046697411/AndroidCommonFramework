package org.jzl.router;

public interface IRoutes {

    void route(String path, RouteMeta meta);

    void service(Class<?> serviceType, RouteMeta routeMeta);

    <T> void objectFactory(Class<T> targetType, IObjectFactory<T> objectFactory);

    void group(String group, IRouteGroup routeGroup);

    void syringe(ISyringe<?> syringe);
}
