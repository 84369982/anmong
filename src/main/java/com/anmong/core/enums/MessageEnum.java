package com.anmong.core.enums;

import java.util.Objects;

/**
 * @author songwenlong
 * 2018/3/19
 */
public class MessageEnum {

    public enum Type {
        文本(1),
        图片(2),
        语音(3),
        视频(4),
        其他(5)
        ;

        public Integer code;

        Type(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (MessageEnum.Type type : MessageEnum.Type.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }

    public enum BizType {
        聊天业务(1),
        发出好友申请通知(2),
        收到好友申请通知(3),
        发出申请通过通知(4),
        收到申请通过通知(5),
        发出申请不通过通知(6),
        发出申请失败通知(7)
        ;

        public Integer code;

        BizType(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (MessageEnum.BizType type : MessageEnum.BizType.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }



}
