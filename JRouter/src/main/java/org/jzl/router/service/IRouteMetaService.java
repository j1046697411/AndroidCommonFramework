package org.jzl.router.service;

import org.jzl.router.IService;
import org.jzl.router.Postcard;

public interface IRouteMetaService extends IService {

    void completionPostcardRouteMeta(Postcard postcard);

}
