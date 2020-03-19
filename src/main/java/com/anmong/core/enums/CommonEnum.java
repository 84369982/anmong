package com.anmong.core.enums;

import java.util.Objects;

public class CommonEnum {
	
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
	
	public enum Is {
        否(0),
        是(1)
        ;

        public Integer code;

        Is(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (Is type : Is.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }
	
	

}
