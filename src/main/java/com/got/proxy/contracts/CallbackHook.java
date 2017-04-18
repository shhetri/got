package com.got.proxy.contracts;

import java.lang.reflect.Method;

public interface CallbackHook {
    void before(Object object, Method method, Object[] args);

    void after(Object object, Method method, Object[] args, Object result);
}
