package org.jzl.router.core;

import android.content.Context;

import org.jzl.lang.util.ObjectUtils;
import org.jzl.router.IObjectFactory;
import org.jzl.router.IRouteGroup;
import org.jzl.router.IRouteHelper;
import org.jzl.router.IService;
import org.jzl.router.ISyringe;
import org.jzl.router.Postcard;
import org.jzl.router.RouteMeta;

import java.util.HashMap;
import java.util.Map;

public class RouteHelperImpl implements IRouteHelper {
    private static final String ROOT_APP_CLASS = "org.jzl.router.routes.JRouter$$Root$$app";


    private Context context;
    private final Map<String, RouteMeta> routeMetas = new HashMap<>();
    private final Map<Class<?>, RouteMeta> serviceRouteMetas = new HashMap<>();
    private final Map<Class<?>, IObjectFactory<?>> objectFactories = new HashMap<>();
    private final Map<String, IRouteGroup> groups = new HashMap<>();

    private final Map<RouteMeta, Postcard> servicePostcards = new HashMap<>();

    @SuppressWarnings("unchecked")
    public void init(Context context) {
        this.context = context;
        try {
            Class<IRouteGroup> type = (Class<IRouteGroup>) Class.forName(ROOT_APP_CLASS);
            type.newInstance().loadInto(this);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void route(String path, RouteMeta meta) {
        this.routeMetas.put(path, meta);
    }

    @Override
    public void service(Class<?> serviceType, RouteMeta routeMeta) {
        this.serviceRouteMetas.put(serviceType, routeMeta);
    }

    @Override
    public <T> void objectFactory(Class<T> targetType, IObjectFactory<T> objectFactory) {
        this.objectFactories.put(targetType, objectFactory);
    }

    @Override
    public void group(String group, IRouteGroup routeGroup) {
        this.groups.put(group, routeGroup);
        routeGroup.loadInto(this);
    }

    @Override
    public void syringe(ISyringe<?> syringe) {
    }

    @Override
    public RouteMeta getRouteMeta(String path) {
        return routeMetas.get(path);
    }

    @Override
    public RouteMeta getServiceRouteMeta(Class<?> serviceType) {
        return serviceRouteMetas.get(serviceType);
    }

    private IObjectFactory<?> getObjectFactory(Class<?> targetType) {
        return objectFactories.get(targetType);
    }

    @Override
    public Postcard buildServicePostcard(RouteMeta routeMeta) {
        Postcard postcard = this.servicePostcards.get(routeMeta);
        if (ObjectUtils.isNull(postcard)) {
            postcard = new Postcard(routeMeta.getGroup(), routeMeta.getPath()).withRouteMeta(routeMeta);
            this.servicePostcards.put(routeMeta, postcard);
        }
        return postcard;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T buildService(Postcard postcard) {
        RouteMeta routeMeta = postcard.getRouteMeta();
        if (ObjectUtils.isNull(postcard.getResult())) {
            IObjectFactory<?> objectFactory = getObjectFactory(routeMeta.getTargetClass());
            T object;
            if (ObjectUtils.nonNull(objectFactory)) {
                object = (T) objectFactory.createObject(postcard);
            } else {
                try {
                    object = (T) routeMeta.getTargetClass().newInstance();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
            if (object instanceof IService) {
                ((IService) object).init(context);
            }
            postcard.setResult(object);
        }
        return postcard.getResult();
    }

}
