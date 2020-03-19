package com.anmong.core.vo.wap.friendapply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author songwenlong
 * 2018/3/16
 */
@ApiModel(value = "好友申请待处理列表")
@Data
public class WapFriendApplyIndexVO {

    @ApiModelProperty(value = "32位UUID", dataType = "String", example = "1aec04c627c44b2eae09-23eb1b92bc26")
    private String id;

    @ApiModelProperty(value = "昵称",dataType = "String", example = "小明")
    private String nickname;

    @ApiModelProperty(value = "头像", dataType = "String",example = "http://www.baidu.com")
    private String headUrl;

    @ApiModelProperty(value = "性别", example = "1男,2女")
    private Short sex;

    @ApiModelProperty(value = "简介", example = "我是小明")
    private String content;

}
