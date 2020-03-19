package com.anmong.core.dto.wap.user;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author songwenlong
 * 2018/3/2
 */
@ApiModel(value = "查询用户详情")
@Data
public class WapBlogUserShowDTO {

    @ApiModelProperty(value = "目标用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String userId;

    @ApiModelProperty(value = "当前登录用户自己的id", example = "123")
    private String id;
}
