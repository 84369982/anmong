package com.anmong.core.vo.wap.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author songwenlong
 * 2018/3/14
 */
@ApiModel(value = "微博展示的用户信息")
@Data
public class WapBlogUserShowVO {

    @ApiModelProperty(value = "32位UUID", dataType = "String", example = "1aec04c627c44b2eae09-23eb1b92bc26")
    private String id;

    @ApiModelProperty(value = "昵称",dataType = "String", example = "小明")
    private String nickname;

    @ApiModelProperty(value = "头像", dataType = "String",example = "http://www.baidu.com")
    private String headUrl;

    @ApiModelProperty(value = "等级", example = "0")
    private Short grade;

    @ApiModelProperty(value = "所在城市", example = "成都")
    private String city;

    @ApiModelProperty(value = "简介", example = "我是小明")
    private String summary;

    @ApiModelProperty(value = "性别", example = "1男,2女")
    private Short sex;

    @ApiModelProperty(value = "关注数", example = "1")
    private Integer attention;

    @ApiModelProperty(value = "粉丝数", example = "1")
    private Integer fans;

    @ApiModelProperty(value = "是否关注", example = "true")
    private Boolean isAttention ;



}
