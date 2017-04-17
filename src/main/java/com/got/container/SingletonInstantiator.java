package com.got.container;

class SingletonInstantiator extends AbstractInstantiator {
    SingletonInstantiator(Application container) {
        super(container);
    }

    @Override
    public <T> T instantiate(Class<T> clazz) {
        if (canInstantiate(clazz)) {
            return clazz.cast(container.getSingletonInstances().get(clazz));
        }

        return successor.instantiate(clazz);
    }

    @Override
    public <T> boolean canInstantiate(Class<T> clazz) {
        return container.getSingletonInstances().containsKey(clazz);
    }
}
