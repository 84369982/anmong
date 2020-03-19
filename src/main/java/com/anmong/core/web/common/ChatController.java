package com.anmong.core.web.common;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.websocket.ChatSocket;
import com.anmong.common.websocket.MessageBody;
import com.anmong.core.dto.wap.chat.WapChatDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author songwenlong
 * 2018/3/14
 */
@RestController
@RequestMapping("wap/chat")
public class ChatController {

    @PostMapping("send")
    @ApiOperation(value = "发送聊天消息",tags = "wap-聊天")
    public DosserReturnBody send(@RequestBody @Valid WapChatDTO dto){
        MessageBody message = new MessageBody();
        /*message.setSuccess(true);
        message.setMessage(dto.getMessage());*/
        //ChatSocket.send(dto.getToUserId(),message);
        return new DosserReturnBodyBuilder().statusOk().build();
    }
}
