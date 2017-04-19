package com.got.event;

import com.got.container.ContainerFactory;
import com.got.container.contracts.Container;
import com.got.event.contracts.Event;
import com.got.event.contracts.EventDispatcher;
import com.got.event.contracts.Listener;

import java.util.List;
import java.util.Map;

public class Dispatcher implements EventDispatcher {
    @Override
    public void dispatch(Event event) {
        Map<Class<? extends Event>, List<Class<? extends Listener>>> mappings = EventMapper.getMappings();
        Container container = ContainerFactory.getDefaultContainer();

        for (Map.Entry<Class<? extends Event>, List<Class<? extends Listener>>> entry : mappings.entrySet()) {
            List<Class<? extends Listener>> listeners = entry.getValue();
            listeners.stream().map(container::make).forEach((Listener listener) -> listener.handle(event));
        }
    }
}
