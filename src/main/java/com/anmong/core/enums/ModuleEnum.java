package com.anmong.core.enums;

import java.util.Objects;

public class ModuleEnum {
	
	public enum Name {
        用户模块("user"),
        微博("blog"),
        ;

        public String code;

        Name(String code) {
            this.code = code;
        }

        public static String getNameByCode(String code) {
            if (code == null) {
                return "";
            }
            for (Name type : Name.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
        
        public static String getCode(String code) {
            if (code == null) {
                return "";
            }
            for (Name type : Name.values()) {
                if (Objects.equals(code, type.code)) {
                    return code;
                }
            }
            return "";
        }
    }
	

}
