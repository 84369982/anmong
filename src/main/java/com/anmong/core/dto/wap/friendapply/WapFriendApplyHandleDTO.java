package com.anmong.core.dto.wap.friendapply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author songwenlong
 * 2018/3/16
 */
@ApiModel(value = "好友申请处理")
@Data
public class WapFriendApplyHandleDTO {

    @ApiModelProperty(value = "业务id", example = "123")
    @NotBlank(message="业务id不允许为空")
    private String id;

    @ApiModelProperty(value = " 用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String userId;

    @ApiModelProperty(value = "是否同意:0否1是", example = "1")
    @NotNull(message="结果不允许为空")
    private Short result;

    @ApiModelProperty(value = " 备注(同意申请时的可选项)", example = "张三")
    private String note;
}
