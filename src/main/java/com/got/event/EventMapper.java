package com.got.event;

import com.got.event.contracts.Event;
import com.got.event.contracts.Listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventMapper {
    private static Map<Class<? extends Event>, List<Class<? extends Listener>>> mappings = new HashMap<>();

    public static void map(Class<? extends Event> event, Class<? extends Listener>... listeners) {
        if (listeners.length == 0) throw new IllegalArgumentException("Event must be mapped to at least one listener");

        mappings.put(event, Arrays.asList(listeners));
    }

    public static Map<Class<? extends Event>, List<Class<? extends Listener>>> getMappings() {
        return mappings;
    }
}
