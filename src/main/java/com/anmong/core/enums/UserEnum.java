package com.anmong.core.enums;

import java.util.Objects;

public class UserEnum {
	
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
	
	public enum Type {
        会员(1),
        管理员(2)
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


}
