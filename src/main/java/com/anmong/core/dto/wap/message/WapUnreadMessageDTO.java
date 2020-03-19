package com.anmong.core.dto.wap.message;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author songwenlong
 * 2018/3/19
 */
@ApiModel(value = "消息记录")
@Data
public class WapUnreadMessageDTO{

    @ApiModelProperty(value = "目标用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String friendId;
    @ApiModelProperty(value = "消息发出用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String userId;

}
