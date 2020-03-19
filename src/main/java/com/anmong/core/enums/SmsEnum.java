package com.anmong.core.enums;

import java.util.Objects;

public class SmsEnum {
	
	public enum Type {
        注册(1),
        登录(2),
        找回密码(3),
        修改密码(4)
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
