package com.anmong.common.websocket;

import lombok.Data;

/**
 * @author songwenlong
 * 2018/3/14
 */
@Data
public class MessageBody {
    private Boolean success;
    private Short type;
    private String message;
    private Short bizType;
}
