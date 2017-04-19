package com.got.container.contracts;

import com.got.proxy.contracts.Proxy;

import java.io.Serializable;

public interface Container {
    <T> void bind(Class<T> placeHolder, Class<? extends T> concrete);

    <T> void bind(Class<T> placeHolder, Class<? extends T> concrete, Boolean shared);

    <T> void singleton(Class<T> placeHolder);

    void prototype(Class<? extends Serializable> placeHolder);

    <T> T make(Class<T> placeHolder);

    void registerProxy(Class<? extends Proxy> proxy, Class<?>... classes);

    void bindDefaultBindings();
}
