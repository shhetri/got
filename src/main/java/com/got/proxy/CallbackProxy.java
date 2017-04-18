package com.got.proxy;

import com.got.proxy.contracts.CallbackHook;
import com.got.proxy.contracts.Proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallbackProxy implements Proxy, Serializable {
    private static CallbackHook hook;
    private Object object;

    public CallbackProxy(Object object) {
        this.object = object;
    }

    public static void setHook(CallbackHook hook) {
        CallbackProxy.hook = hook;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            hook.before(object, method, args);
            result = method.invoke(object, args);
            hook.after(object, method, args, result);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        }

        return result;
    }
}
