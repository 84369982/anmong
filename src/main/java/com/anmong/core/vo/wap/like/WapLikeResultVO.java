package com.anmong.core.vo.wap.like;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author songwenlong
 * 2018/3/12
 */
@ApiModel(value = "点赞返回结果")
@Data
public class WapLikeResultVO {

    @ApiModelProperty(value = "返回结果：1点赞，2.取消点赞", example = "1")
    private Short result;
}
