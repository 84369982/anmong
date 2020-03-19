package com.anmong.common.websocket;

import lombok.Data;

import javax.websocket.Session;

/**
 * @author songwenlong
 * 2018/3/14
 */
@Data
public class WebSocketClient {
    private String userId;

    private Session session;

}
