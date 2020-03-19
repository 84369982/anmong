package com.anmong.core.vo.wap.attention;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author songwenlong
 * 2018/3/13
 */
@Data
@ApiModel(value = "关注/粉丝 列表")
public class WapAttentionIndexVO {

    @ApiModelProperty(value = "用户id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    private String userId;
    @ApiModelProperty(value = "昵称", dataType = "String", example = "张三")
    private String nickname;
    @ApiModelProperty(value = "头像地址", example = "http://www.baidu.com")
    private String headUrl;
    @ApiModelProperty(value = "简介", dataType = "String", example = "简介")
    private String summary;
}
