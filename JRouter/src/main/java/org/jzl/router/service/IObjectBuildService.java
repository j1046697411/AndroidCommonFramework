package org.jzl.router.service;

import android.content.Context;

import org.jzl.router.IService;
import org.jzl.router.Postcard;

public interface IObjectBuildService extends IService {

    Object buildObject(Context context, Postcard postcard);
    
}
