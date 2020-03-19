package com.anmong.core.web.wap;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.websocket.ChatSocket;
import com.anmong.common.websocket.MessageBody;
import com.anmong.core.dto.wap.chat.WapChatDTO;
import com.anmong.core.dto.wap.message.WapMessageCreateDTO;
import com.anmong.core.dto.wap.message.WapUnreadMessageDTO;
import com.anmong.core.service.wap.WapMessageService;
import com.anmong.core.vo.wap.message.WapUnreadMessageIndexVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author songwenlong
 * 2018/3/14
 */
@RestController
@RequestMapping("wap/message")
public class WapMessageController {

    @Autowired
    private WapMessageService wapMessageService;

    @PostMapping("send")
    @ApiOperation(value = "同步发送聊天消息",tags = "wap-聊天")
    public DosserReturnBody send(@RequestBody @Valid WapMessageCreateDTO dto){
        wapMessageService.add(dto);
        return new DosserReturnBodyBuilder().statusOk().build();
    }

    @GetMapping("find-unread-message")
    @ApiOperation(value = "获取未读聊天消息",tags = "wap-聊天",response = WapUnreadMessageIndexVO.class)
    public DosserReturnBody findUnreadMessage(@Valid WapUnreadMessageDTO dto){
        return new DosserReturnBodyBuilder().collection(wapMessageService.findUnreadMessage(dto)).build();
    }
}
