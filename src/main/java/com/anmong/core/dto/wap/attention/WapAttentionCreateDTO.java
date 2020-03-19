package com.anmong.core.dto.wap.attention;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author songwenlong
 * 2018/3/12
 */
@ApiModel(value = "添加/取消 关注")
@Data
public class WapAttentionCreateDTO {

    @ApiModelProperty(value = "关注目标用户id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    @NotBlank(message="目标用户id不能为空")
    private String userId;
    @ApiModelProperty(value = "用户自己id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    @NotBlank(message = "用户Id不能为空")
    private String createMan;

}
