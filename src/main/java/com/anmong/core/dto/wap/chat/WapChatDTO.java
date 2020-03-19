package com.anmong.core.dto.wap.chat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author songwenlong
 * 2018/3/14
 */
@Data
@ApiModel(value = "聊天")
public class WapChatDTO {
    private String toUserId;
    private String fromUserId;
    private String message;
}
