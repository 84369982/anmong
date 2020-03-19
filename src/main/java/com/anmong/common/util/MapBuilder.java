package com.anmong.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapBuilder {
    private Map<String, Object> map = new HashMap();

    public MapBuilder() {
    }

    public MapBuilder add(String k, Object v) {
        this.map.put(k, v);
        return this;
    }

    public Map<String, Object> build() {
        return this.map;
    }

    public Map<String, String> buildString() {
        Map<String, String> map2 = new HashMap();
        Iterator var2 = this.map.entrySet().iterator();

        while(var2.hasNext()) {
            Entry<String, Object> entry = (Entry)var2.next();
            map2.put(entry.getKey(), (String)entry.getValue());
        }

        return map2;
    }

    public Map buildRaw() {
        return this.map;
    }
}
