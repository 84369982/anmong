package com.anmong.core.enums;

import java.util.Objects;

/**
 * @author songwenlong
 * 2018/3/2
 */
public class CommentEnum {


    public enum CommentType {
        文章评论(1),
        针对评论的评论(2),
        ;

        public Integer code;

        CommentType(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (CommentEnum.CommentType type : CommentEnum.CommentType.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }

    public enum ContentType {
        图文(1),
        语音(2),
        ;

        public Integer code;

        ContentType(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (CommentEnum.ContentType type : CommentEnum.ContentType.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }
}
