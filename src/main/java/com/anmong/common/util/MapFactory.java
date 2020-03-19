package com.anmong.common.util;

import java.util.HashMap;

public class MapFactory {
    public MapFactory() {
    }

    public static HashMap<String, Object> of() {
        HashMap<String, Object> map = new HashMap();
        return map;
    }

    public static HashMap<String, Object> of(String k1, Object v1) {
        HashMap<String, Object> map = new HashMap();
        map.put(k1, v1);
        return map;
    }

    public static HashMap<String, Object> of(String k1, Object v1, String k2, Object v2) {
        HashMap<String, Object> map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static HashMap<String, Object> of(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        HashMap<String, Object> map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static HashMap<String, Object> of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        HashMap<String, Object> map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static HashMap<String, Object> of(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
        HashMap<String, Object> map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }

    public static HashMap<String, String> stringOf(String k1, String v1) {
        HashMap<String, String> map = new HashMap();
        map.put(k1, v1);
        return map;
    }

    public static HashMap<String, String> stringOf(String k1, String v1, String k2, String v2) {
        HashMap<String, String> map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static HashMap<String, String> stringOf(String k1, String v1, String k2, String v2, String k3, String v3) {
        HashMap<String, String> map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static HashMap<String, String> stringOf(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4) {
        HashMap<String, String> map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static HashMap<String, String> stringOf(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4, String k5, String v5) {
        HashMap<String, String> map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }

    public static HashMap rawOf() {
        HashMap map = new HashMap();
        return map;
    }

    public static HashMap rawOf(Object k1, Object v1) {
        HashMap map = new HashMap();
        map.put(k1, v1);
        return map;
    }

    public static HashMap rawOf(Object k1, Object v1, Object k2, Object v2) {
        HashMap map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static HashMap rawOf(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
        HashMap map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static HashMap rawOf(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
        HashMap map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static HashMap rawOf(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4, Object k5, Object v5) {
        HashMap map = new HashMap();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }
}
