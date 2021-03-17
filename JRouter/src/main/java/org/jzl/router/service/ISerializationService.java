package org.jzl.router.service;

import org.jzl.router.IService;

public interface ISerializationService extends IService {

    <T> T jsonToObject(String json, Class<T> type);

    String objectToJson(Object object);
    
}
