package com.anmong.core.enums;

import java.util.Objects;

/**
 * @author songwenlong
 * 2018/3/2
 */
public class FriendApplyEnum {


    public enum state {
        待验证(1),
        通过(2),
        ;

        public Integer code;

        state(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (FriendApplyEnum.state type : FriendApplyEnum.state.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }


}
