package com.anmong.core.enums;

import java.util.Objects;

public class FileEnum {
	
	public enum FileType {
        图片(1),
        视频(2),
        音频(3)
        ;

        public Integer code;

        FileType(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (FileType type : FileType.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }
	
	
	public enum StoreType {
        本地(1),
        静态资源服务器(2),
        网易云(3),
        腾讯云1(4),
        腾讯云2(5),
        七牛云(6),
        阿里云(7),
        优刻云(8)
        ;

        public Integer code;

        StoreType(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (StoreType type : StoreType.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }
	
	/**
	 * 验证类型
	 * @return
	 */
	public static boolean isAllowType(Integer fileType) {
		boolean result = false;
		if(fileType == null) {
			return false;
		}
		else {
			  for (FileType type : FileType.values()) {
	                if (Objects.equals(fileType, type.code)) {
	                	result = true;
	                	break;
	                }
	            }
		}
		return result;
	}
	

}
