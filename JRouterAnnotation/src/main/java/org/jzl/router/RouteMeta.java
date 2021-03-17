package org.jzl.router;

import java.io.Serializable;

public final class RouteMeta implements Serializable {

    private final String path;
    private final String group;
    private final RouteType routeType;
    private final Class<?> targetClass;

    RouteMeta(String path, String group, RouteType routeType, Class<?> targetClass) {
        this.path = path;
        this.group = group;
        this.routeType = routeType;
        this.targetClass = targetClass;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public String getPath() {
        return path;
    }

    public String getGroup() {
        return group;
    }

    public static RouteMeta build(String path, String group, RouteType routeType, Class<?> targetClass){
        return new RouteMeta(path, group, routeType, targetClass);
    }
}
