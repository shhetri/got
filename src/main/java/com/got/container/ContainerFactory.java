package com.got.container;

import com.got.container.contracts.Container;

public final class ContainerFactory {
    public static Container getDefaultContainer() {
        return Application.getInstance();
    }
}
