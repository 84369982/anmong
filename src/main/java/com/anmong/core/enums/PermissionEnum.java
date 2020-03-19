package com.anmong.core.enums;

import java.util.Objects;

public class PermissionEnum {
	
	public enum Type {
        功能(1),
        菜单(2),
        页面(3)
        ;

        public Integer code;

        Type(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (Type type : Type.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }
	
	public enum State {
        禁用(0),
        启用(1)
        ;

        public Integer code;

        State(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (State type : State.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }
	
	public enum IsRoot {
        否(0),
        是(1)
        ;

        public Integer code;
        IsRoot(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (IsRoot type : IsRoot.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }
	


}
