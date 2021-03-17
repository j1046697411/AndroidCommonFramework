package org.jzl.router;

public interface IObjectFactory<T> {

    T createObject(Postcard postcard);

}
