package com.got.container;

import com.got.container.contracts.Instantiator;

class ObjectResolver {
    static Instantiator getinstantiator(Application container) {
        Instantiator singletonInstantiator = new SingletonInstantiator(container);
        Instantiator prototypeInstantiator = new PrototypeInstantiator(container);
        Instantiator newInstantiator = new NewInstantiator(container);
        singletonInstantiator.setSuccessor(prototypeInstantiator);
        prototypeInstantiator.setSuccessor(newInstantiator);

        return singletonInstantiator;
    }
}
