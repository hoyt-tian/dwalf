package com.hoyt_tian.dwalf.event;

import java.util.HashMap;
import java.util.Map;

public abstract class Event<T> {
    protected T type;
    protected Map<String, Object> data;
    private boolean stopped = false;

    public Event() {

    }

    public Event(T type) {
        this.setType(type);
    }

    public Event<T> setType(T type) {
        this.type = type;
        return this;
    }

    public T getType() {
        return this.type;
    }

    public void setVal(String key, Object val) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, val);
    }

    public Object get(String key) {
        return getOrDefault(key, null);
    }

    public Object getOrDefault(String key, Object defaults) {
        if (data != null && data.containsKey(key)) {
            return data.get(key);
        }
        return defaults;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        this.stopped = false;
    }
}
