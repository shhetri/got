package com.got.container;

import com.got.container.contracts.Container;
import com.got.container.contracts.ContainerAware;
import com.got.container.contracts.Instantiator;
import com.got.proxy.contracts.Proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Application implements Container, Serializable {
    private static Application instance = null;
    private final Map<Class<?>, Map<Class<?>, Boolean>> bindings = new HashMap<>();
    private final Map<Class<?>, Object> singletonInstances = new HashMap<>();
    private final Map<Class<?>, Object> prototypeInstances = new HashMap<>();
    private final List<Class<?>> prototypeBindings = new ArrayList<>();
    private final Map<Class<? extends Proxy>, List<Class<?>>> proxyMappings = new HashMap<>();

    private Application() {

    }

    static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }

        return instance;
    }

    private void dropStaleInstance(Class<?> placeHolder) {
        singletonInstances.remove(placeHolder);
        prototypeInstances.remove(placeHolder);
    }

    @Override
    public <T> void bind(Class<T> placeHolder, Class<? extends T> concrete) {
        bind(placeHolder, concrete, false);
    }

    @Override
    public <T> void bind(Class<T> placeHolder, Class<? extends T> concrete, Boolean shared) {
        Map<Class<?>, Boolean> map = new HashMap<>();
        map.put(concrete, shared);
        bindings.put(placeHolder, map);

        dropStaleInstance(placeHolder);
    }

    @Override
    public <T> void singleton(Class<T> placeHolder) {
        bind(placeHolder, placeHolder, true);
    }

    @Override
    public void prototype(Class<? extends Serializable> placeHolder) {
        prototypeBindings.add(placeHolder);

        dropStaleInstance(placeHolder);
    }

    Map<Class<?>, Map<Class<?>, Boolean>> getBindings() {
        return bindings;
    }

    Map<Class<?>, Object> getSingletonInstances() {
        return singletonInstances;
    }

    Map<Class<?>, Object> getPrototypeInstances() {
        return prototypeInstances;
    }

    List<Class<?>> getPrototypeBindings() {
        return prototypeBindings;
    }

    @Override
    public <T> T make(Class<T> placeHolder) {
        Instantiator instantiator = ObjectResolver.getinstantiator(this);

        return instantiator.instantiate(placeHolder);
    }

    @Override
    public void registerProxy(Class<? extends Proxy> proxy, Class<?>... classes) {
        List<Class<?>> classesToBeProxied = Arrays.asList(classes);

        proxyMappings.put(proxy, classesToBeProxied);
    }

    <T> void passContainer(T concrete) {
        if (concrete instanceof ContainerAware) {
            ((ContainerAware) concrete).setContainer(this);
        }
    }

    <T> Object wrapProxy(Class<T> clazz, T concrete) {
        for (Map.Entry<Class<? extends Proxy>, List<Class<?>>> pair : proxyMappings.entrySet()) {
            if (pair.getValue().contains(clazz)) {
                try {
                    Class<? extends Proxy> proxy = pair.getKey();
                    return java.lang.reflect.Proxy.newProxyInstance(concrete.getClass().getClassLoader(), concrete.getClass().getInterfaces(), (InvocationHandler) proxy.getConstructors()[0].newInstance(concrete));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return concrete;
    }
}
