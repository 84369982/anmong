package com.anmong.core.dto.wap.friendapply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author songwenlong
 * 2018/3/16
 */
@ApiModel(value = "好友申请")
@Data
public class WapFriendApplyCreateDTO {
    @ApiModelProperty(value = "目标用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String friendId;

    @ApiModelProperty(value = "验证消息", example = "123")
    private String content;

    @ApiModelProperty(value = "创建申请用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String createMan;
}
