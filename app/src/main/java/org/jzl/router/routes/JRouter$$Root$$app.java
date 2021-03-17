package org.jzl.router.routes;

import org.jzl.android.app.TestActivity;
import org.jzl.router.IRouteGroup;
import org.jzl.router.IRoutes;
import org.jzl.router.RouteMeta;
import org.jzl.router.RouteType;

public class JRouter$$Root$$app implements IRouteGroup {

    @Override
    public void loadInto(IRoutes routes) {
        routes.group("root", new JRouter$$Services$$app());
        routes.route("/test", RouteMeta.build("/test", "test", RouteType.ACTIVITY, TestActivity.class));
    }
}
