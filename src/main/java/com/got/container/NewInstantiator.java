package com.got.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Map;

class NewInstantiator extends AbstractInstantiator {
    NewInstantiator(Application container) {
        super(container);
    }

    @Override
    public <T> T instantiate(Class<T> clazz) {
        if (canInstantiate(clazz)) {
            T concrete = null;
            Map<Class<?>, Boolean> concreteMapping = container.getBindings().get(clazz);
            Map.Entry<Class<?>, Boolean> concreteMappingEntry = null;

            try {
                if (concreteMapping != null) {
                    concreteMappingEntry = concreteMapping.entrySet().iterator().next();
                    concrete = build((Class<T>) concreteMappingEntry.getKey());
                } else {
                    concrete = build(clazz);
                }

                concrete = (T) container.wrapProxy(clazz, concrete);
                updateInstances(clazz, concrete, concreteMappingEntry);
                container.passContainer(concrete);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

            return concrete;
        }

        return null;
    }

    private <T> T build(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (clazz.isPrimitive())
            throw new InstantiationException("Cannot instantiate primitive type: " + clazz.getName());
        if (clazz.isInterface())
            throw new InstantiationException("Cannot instantiate interface: " + clazz.getSimpleName() + ". There is no binding in the container");

        Constructor[] constructors = clazz.getConstructors();
        Constructor instantiatingConstructor = constructors[0];

        for (int i = 1; i < constructors.length; i++) {
            if (instantiatingConstructor.getParameterCount() > constructors[i].getParameterCount())
                instantiatingConstructor = constructors[i];
        }

        Parameter[] parameters = instantiatingConstructor.getParameters();
        Object[] dependencies = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Class<?> dependencyClass = parameters[i].getType();
            dependencies[i] = instantiate(dependencyClass);
            if (dependencies[i] == null) return null;
        }

        return clazz.cast(instantiatingConstructor.newInstance(dependencies));
    }

    @Override
    public <T> boolean canInstantiate(Class<T> clazz) {
        return true;
    }

    private <T> void updateInstances(Class<T> placeHolder, T concrete, Map.Entry<Class<?>, Boolean> concreteMappingEntry) {
        if (concreteMappingEntry != null && concreteMappingEntry.getValue()) {
            container.getSingletonInstances().put(placeHolder, concrete);
        }

        if (container.getPrototypeBindings().contains(placeHolder)) {
            container.getPrototypeInstances().put(placeHolder, concrete);
        }
    }
}
