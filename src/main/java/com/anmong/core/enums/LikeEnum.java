package com.anmong.core.enums;

import java.util.Objects;

/**
 * @author songwenlong
 * 2018/3/12
 */
public class LikeEnum {

    public enum Result {
        点赞(1),
        取消点赞(2),
        ;

        public Integer code;

        Result(Integer code) {
            this.code = code;
        }


    }

    public enum Type {
        文章点赞(1),
        评论点赞(2),
        ;

        public Integer code;

        Type(Integer code) {
            this.code = code;
        }


    }
}
