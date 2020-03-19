package com.anmong.core.dto.wap.like;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author songwenlong
 * 2018/3/12
 */
@ApiModel(value = "添加点赞")
@Data
public class WapLikeCreateDTO {

    @ApiModelProperty(value = "业务id（文章或评论id）", example = "1aec04c627c44b2eae0923eb1b92bc26")
    @NotBlank(message="业务id不能为空")
    private String bizId;
    @ApiModelProperty(value = "点赞类型：1文章点赞,2评论点赞", example = "1")
    @NotNull(message="点赞类型不能为空")
    private Short type;
    @ApiModelProperty(value = "用户id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    @NotBlank(message = "用户Id不能为空")
    private String createMan;

}
