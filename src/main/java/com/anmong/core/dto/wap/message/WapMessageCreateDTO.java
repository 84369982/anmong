package com.anmong.core.dto.wap.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author songwenlong
 * 2018/3/19
 */
@ApiModel(value = "发送消息")
@Data
public class WapMessageCreateDTO {

    @ApiModelProperty(value = "目标用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String targetUserId;
    @ApiModelProperty(value = "内容类型：1.文本2.图片 3.语音 4.视频 5.其他", example = "1")
    @NotNull(message="内容类型不能为空")
    private Short type;
    @ApiModelProperty(value = "内容类型：聊天业务(1)发出好友申请通知(2)收到好友申请通知(3)发出申请通过通知(4) 收到申请通过通知(5) 发出申请不通过通知(6),发出申请失败通知(7)", example = "1")
    @NotNull(message="内容类型不能为空")
    private Short bizType;
    @ApiModelProperty(value = "消息内容", example = "123")
    private String content;
    @ApiModelProperty(value = "消息发出用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String createMan;

}
