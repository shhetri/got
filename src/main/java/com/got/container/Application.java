package com.got.container;

import com.got.container.contracts.Container;
import com.got.container.contracts.ContainerAware;
import com.got.container.contracts.Instantiator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application implements Container, Serializable {
    private static Application instance = null;
    private final Map<Class<?>, Map<Class<?>, Boolean>> bindings = new HashMap<>();
    private final Map<Class<?>, Object> singletonInstances = new HashMap<>();
    private final Map<Class<?>, Object> prototypeInstances = new HashMap<>();
    private final List<Class<?>> prototypeBindings = new ArrayList<>();

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

    <T> void passContainer(T concrete) {
        if (concrete instanceof ContainerAware) {
            ((ContainerAware) concrete).setContainer(this);
        }
    }
}
