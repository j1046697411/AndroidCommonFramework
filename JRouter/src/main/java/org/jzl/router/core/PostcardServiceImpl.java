package org.jzl.router.core;

import android.content.Context;

import org.jzl.router.Postcard;
import org.jzl.router.RouteMeta;
import org.jzl.router.service.IPostcardService;

public class PostcardServiceImpl implements IPostcardService {

    @Override
    public void init(Context context) {
    }

    @Override
    public Postcard createPostcard(RouteMeta routeMeta) {
        return new Postcard(routeMeta.getGroup(), routeMeta.getPath()).withRouteMeta(routeMeta);
    }

    @Override
    public Postcard createPostcard(String path) {
        return new Postcard(getGroupName(path), path);
    }

    @Override
    public Postcard forward(String path, Postcard oldPostcard) {
        return new Postcard(getGroupName(path), path, oldPostcard);
    }


    private String getGroupName(String path){
        return path;
    }

}
