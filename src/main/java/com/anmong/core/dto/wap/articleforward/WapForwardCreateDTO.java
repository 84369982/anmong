package com.anmong.core.dto.wap.articleforward;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author songwenlong
 * 2018/3/13
 */
@ApiModel(value = "转载文章")
@Data
public class WapForwardCreateDTO {

    @ApiModelProperty(value = "转载根文章id", example = "123")
    @NotBlank(message = "文章Id不能为空")
    private String sourceId;

    @ApiModelProperty(value = "用户id", example = "123")
    @NotBlank(message = "用户Id不能为空")
    private String createMan;

    @ApiModelProperty(value = "文章内容", example = "内容")
    private String txt;




}
