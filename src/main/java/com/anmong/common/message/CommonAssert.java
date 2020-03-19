package com.anmong.common.message;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.StringUtils;

public class CommonAssert {
    public static void notNull(Object object, String message) {
        if (StringUtils.isEmpty(object)) {
            throw CommonException.badRequests(message);
        }
    }

    public static void notNull(Object object, String message, Map<String, String> errorMap) {
        if (StringUtils.isEmpty(object)) {
            throw CommonException.badRequests(message, errorMap);
        }
    }


    public static void mustEquals(Object object1, Object object2, String message) {
        if (!object1.getClass().equals(object2.getClass())) {
            throw new RuntimeException("mustEquals的参数类型必须一致");
        }
        if (!Objects.equals(object1, object2)) {
            throw CommonException.badRequests(message);
        }
    }

    public static <T> void mustIn(T object1, List<T> list, String message) {
        if (!list.contains(object1)) {
            throw CommonException.badRequests(message);
        }
    }


    public static void mustEquals(Object object1, Object object2, String message, Map<String, String> errorMap) {
        if (!object1.getClass().equals(object2.getClass())) {
            throw new RuntimeException("mustEquals的参数类型必须一致");
        }
        if (!Objects.equals(object1, object2)) {
            throw CommonException.badRequests(message, errorMap);
        }
    }

    public static void mustTrue(boolean b, String message) {
        if (!b) {
            throw CommonException.badRequests(message);
        }
    }

    public static void mustTrue(boolean b, String message, Map<String, String> errorMap) {
        if (!b) {
            throw CommonException.badRequests(message, errorMap);
        }
    }

    public static void mustFalse(boolean b, String message) {
        if (b) {
            throw CommonException.badRequests(message);
        }
    }

    public static void mustFalse(boolean b, String message, Map<String, String> errorMap) {
        if (b) {
            throw CommonException.badRequests(message, errorMap);
        }
    }

}
