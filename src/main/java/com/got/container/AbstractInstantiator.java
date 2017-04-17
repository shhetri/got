package com.got.container;

import com.got.container.contracts.Instantiator;

abstract class AbstractInstantiator implements Instantiator {
    Instantiator successor;
    Application container;

    AbstractInstantiator(Application container) {
        this.container = container;
    }

    @Override
    public void setSuccessor(Instantiator successor) {
        this.successor = successor;
    }
}
