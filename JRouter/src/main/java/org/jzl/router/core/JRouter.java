package org.jzl.router.core;

import android.app.Application;
import android.content.Context;

import org.jzl.lang.fun.Consumer;
import org.jzl.lang.util.ObjectUtils;
import org.jzl.router.INavigationCallback;
import org.jzl.router.INavigator;
import org.jzl.router.IRouteHelper;
import org.jzl.router.IService;
import org.jzl.router.Postcard;
import org.jzl.router.RouteMeta;
import org.jzl.router.service.IInterceptorService;
import org.jzl.router.service.INavigationService;
import org.jzl.router.service.IPostcardService;
import org.jzl.router.service.IRouteMetaService;

public class JRouter {

    private Application application;

    private IInterceptorService interceptorService;
    private final INavigator navigator;
    private final RouteHelperImpl routes;

    private JRouter() {
        this.navigator = new RouteNavigator(this);
        this.routes = new RouteHelperImpl();
    }

    public static JRouter getInstance() {
        return Holder.SIN;
    }

    public void init(Application application) {
        this.application = application;
        routes.init(application);
    }

    public IRouteHelper getRouteHelper() {
        return this.routes;
    }

    public <T extends IService> T navigation(Class<T> type) {
        RouteMeta routeMeta = routes.getServiceRouteMeta(type);
        Postcard postcard = routes.buildServicePostcard(routeMeta);
        return routes.buildService(postcard);
    }

    public Object navigation(Context context, String path, Consumer<Postcard> consumer) {
        return navigation(context, path, consumer, null);
    }

    public Object navigation(Context context, String path, Consumer<Postcard> consumer, INavigationCallback navigationCallback) {
        Context currentContext = ObjectUtils.get(context, this.application);
        Postcard postcard = createPostcard(path);
        if (ObjectUtils.nonNull(consumer)) {
            consumer.accept(postcard);
        }
        return doInterceptions(currentContext, postcard, navigationCallback);
    }

    protected Postcard createPostcard(String path) {
        return navigator.createPostcard(path);
    }

    protected Object doInterceptions(Context context, Postcard postcard, INavigationCallback navigationCallback) {
        if (ObjectUtils.isNull(interceptorService)) {
            this.interceptorService = navigation(IInterceptorService.class);
        }
        if (postcard.isGreenChannel()) {
            return navigator.navigation(context, navigator, postcard, navigationCallback);
        } else {
            return interceptorService.doInterceptions(context, navigator, postcard, navigationCallback);
        }
    }

    private static class Holder {
        private static final JRouter SIN = new JRouter();
    }

    private static class RouteNavigator implements INavigator {

        private final JRouter router;
        private INavigationService navigationService;
        private IPostcardService postcardService;
        private IRouteMetaService routeMetaService;

        RouteNavigator(JRouter router) {
            this.router = router;
        }

        @Override
        public Postcard createPostcard(String path) {
            if (ObjectUtils.isNull(postcardService)) {
                this.postcardService = router.navigation(IPostcardService.class);
            }
            Postcard postcard = postcardService.createPostcard(path);
            completionPostcardRouteMeta(postcard);
            return postcard;
        }

        @Override
        public Postcard forward(String path, Postcard oldPostcard) {
            if (ObjectUtils.isNull(postcardService)) {
                this.postcardService = router.navigation(IPostcardService.class);
            }
            Postcard postcard = postcardService.forward(path, oldPostcard);
            completionPostcardRouteMeta(postcard);
            return postcard;
        }

        @Override
        public Object navigation(Context context, INavigator navigator, Postcard postcard, INavigationCallback navigationCallback) {
            if (ObjectUtils.isNull(navigationService)) {
                navigationService = router.navigation(INavigationService.class);
            }
            return navigationService.navigation(context, navigator, postcard, navigationCallback);
        }

        protected void completionPostcardRouteMeta(Postcard postcard) {
            if (ObjectUtils.isNull(routeMetaService)) {
                routeMetaService = router.navigation(IRouteMetaService.class);
            }
            routeMetaService.completionPostcardRouteMeta(postcard);
        }

    }
}
