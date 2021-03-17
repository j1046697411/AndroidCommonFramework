package org.jzl.router.service;

import org.jzl.router.IService;
import org.jzl.router.Postcard;
import org.jzl.router.RouteMeta;

public interface IPostcardService extends IService {

    Postcard createPostcard(RouteMeta routeMeta);

    Postcard createPostcard(String path);

    Postcard forward(String path, Postcard oldPostcard);

}
