package com.anmong.core.dto.wap.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author songwenlong
 * 2018/3/16
 */
@ApiModel(value = "搜索")
@Data
public class WapUserSearchDTO {

    @ApiModelProperty(value = "用户昵称", example = "13618022444")
    @NotBlank(message = "昵称!")
    private String nickname;
}
