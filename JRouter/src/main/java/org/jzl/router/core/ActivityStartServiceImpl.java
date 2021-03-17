package org.jzl.router.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityCompat;

import org.jzl.lang.util.StringUtils;
import org.jzl.router.INavigationCallback;
import org.jzl.router.Postcard;
import org.jzl.router.RouteMeta;
import org.jzl.router.service.IActivityStartService;

public class ActivityStartServiceImpl implements IActivityStartService {

    @Override
    public void init(Context context) {

    }

    @Override
    public Object startActivity(Context context, Postcard postcard, RouteMeta routeMeta, INavigationCallback navigationCallback) {
        Intent intent = new Intent(context, routeMeta.getTargetClass());
        intent.setFlags(postcard.getFlags());
        if (StringUtils.nonEmpty(postcard.getAction())) {
            intent.setAction(postcard.getAction());
        }
        if (postcard.getRequestCode() != -1) {
            if (context instanceof Activity) {
                ActivityCompat.startActivityForResult((Activity) context, intent, postcard.getRequestCode(), null);
            }
        } else {
            ActivityCompat.startActivity(context, intent, null);
        }
        return null;
    }


}
