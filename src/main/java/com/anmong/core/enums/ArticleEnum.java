package com.anmong.core.enums;

import java.util.Objects;

/**
 * @author songwenlong
 * 2018/3/2
 */
public class ArticleEnum {


    public enum WapIndexType {
        热门(1),
        最新(2)
        ;

        public Integer code;

        WapIndexType(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (ArticleEnum.WapIndexType type : ArticleEnum.WapIndexType.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }

    public enum ArticleType {
        微博(1),
        自媒体(2),
        广告(3),
        ;

        public Integer code;

        ArticleType(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (ArticleEnum.ArticleType type : ArticleEnum.ArticleType.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }

    public enum ContentType {
        图文(1),
        视频(2),
        ;

        public Integer code;

        ContentType(Integer code) {
            this.code = code;
        }

        public static String getNameByCode(Integer code) {
            if (code == null) {
                return "";
            }
            for (ArticleEnum.ContentType type : ArticleEnum.ContentType.values()) {
                if (Objects.equals(code, type.code)) {
                    return type.name();
                }
            }
            return "";
        }
    }
}
