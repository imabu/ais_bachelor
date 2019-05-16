package ru.bmstu.view;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private static Map<String, Object> contextObjects;

    public static void init() {
        if (contextObjects == null) {
            contextObjects =  new HashMap<>();
        }
    }

    public static Object getContextObject(String key) {

        return contextObjects.get(key);
    }

    public static Object removeContextObject(String key) {
        return contextObjects.remove(key);
    }

    public static void addContextObject(String key, Object value) {
        contextObjects.put(key, value);
    }

}
