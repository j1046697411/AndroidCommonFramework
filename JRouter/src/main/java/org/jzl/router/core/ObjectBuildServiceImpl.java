package org.jzl.router.core;

import android.content.Context;

import org.jzl.router.IRouteHelper;
import org.jzl.router.Postcard;
import org.jzl.router.service.IObjectBuildService;

public class ObjectBuildServiceImpl implements IObjectBuildService {

    private IRouteHelper routeHelper;

    @Override
    public void init(Context context) {
        this.routeHelper = JRouter.getInstance().getRouteHelper();
    }

    @Override
    public Object buildObject(Context context, Postcard postcard) {
        return routeHelper.buildService(postcard);
    }

}
