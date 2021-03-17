package org.jzl.router;

public interface INavigationCallback {

    void preNavigation(Postcard postcard);

    void onInterrupt(Postcard postcard, Throwable throwable);

}
