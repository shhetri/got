package com.got.container;

import java.io.*;

class PrototypeInstantiator extends AbstractInstantiator {
    PrototypeInstantiator(Application container) {
        super(container);
    }

    @Override
    public <T> T instantiate(Class<T> clazz) {
        if (canInstantiate(clazz)) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(container.getPrototypeInstances().get(clazz));
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                T prototypeInstance = clazz.cast(objectInputStream.readObject());
                container.passContainer(prototypeInstance);

                return prototypeInstance;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        return successor.instantiate(clazz);
    }

    @Override
    public <T> boolean canInstantiate(Class<T> clazz) {
        return container.getPrototypeInstances().containsKey(clazz);
    }
}
