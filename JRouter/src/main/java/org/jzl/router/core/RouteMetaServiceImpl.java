package org.jzl.router.core;

import android.content.Context;

import org.jzl.router.IRouteHelper;
import org.jzl.router.Postcard;
import org.jzl.router.service.IRouteMetaService;

public class RouteMetaServiceImpl implements IRouteMetaService {

    private IRouteHelper routeHelper;

    @Override
    public void init(Context context) {
        this.routeHelper = JRouter.getInstance().getRouteHelper();
    }

    @Override
    public void completionPostcardRouteMeta(Postcard postcard) {
        postcard.withRouteMeta(routeHelper.getRouteMeta(postcard.getPath()));
    }
}
