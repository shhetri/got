package com.got.container.contracts;

public interface Instantiator {
    <T> T instantiate(Class<T> clazz);

    <T> boolean canInstantiate(Class<T> clazz);

    void setSuccessor(Instantiator successor);
}
