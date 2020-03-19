package com.anmong.common.websocket;

import com.alibaba.fastjson.JSON;
import com.anmong.core.entity.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

/**
 * @author songwenlong
 * 2018/3/14
 */
@ServerEndpoint("/ws/chat")
@Slf4j
public class ChatSocket {

    private static LinkedList<WebSocketClient> onlineUserList = new LinkedList<>();

    @OnMessage
    public void onMessage(String message, Session session) {
        /*String userid = session.getA*/


    }

    @OnOpen
    public void onOpen(Session session){
        session.setMaxTextMessageBufferSize(104857600);
        Map<String, List<String>> parameter = session.getRequestParameterMap();
        String id = parameter.get("id").get(0);
        WebSocketClient client = new WebSocketClient();
        client.setUserId(id);
        client.setSession(session);
        onlineUserList.add(client);
    }

    @OnClose
    public void onClose(CloseReason reason,Session session) {
        Map<String, List<String>> parameter = session.getRequestParameterMap();
        String id = parameter.get("id").get(0);
        removeByUserId(id,session);
        log.info("Connection closed");
    }

    @OnError
    public void onError(Throwable t) {
        log.error("在线连接失败:{}",t.getMessage());;
    }

    private static List<WebSocketClient> getByUserId(String id){
        List<WebSocketClient> clients = new ArrayList<>();
        for (WebSocketClient client : onlineUserList){
            if (client.getUserId().equals(id)){
                clients.add(client);
            }
        }
        return clients;
    }

    public static boolean send(String targetUser,Message message){
        boolean result = false;
        List<WebSocketClient> clients = getByUserId(targetUser);
        if (!clients.isEmpty()){
            for (WebSocketClient client : clients) {
                client.getSession().getAsyncRemote().sendText(JSON.toJSONStringWithDateFormat(message,"yyyy-MM-dd HH:mm:ss"));
            }
            result = true;
        }
        return result;

    }

    private static void removeByUserId(String id,Session session){
        Iterator iterator = onlineUserList.iterator();
        while (iterator.hasNext()){
            WebSocketClient client = (WebSocketClient)iterator.next();
            if (client.getUserId().equals(id) && client.getSession().getId().equals(session.getId())){
                iterator.remove();
                break;
            }

        }
    }

}
