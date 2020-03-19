package com.anmong.core.vo.wap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel(value = "登录信息")
public class WapUserShowVO {

    @ApiModelProperty(value = "36位UUID", dataType = "String", example = "1aec04c6-27c4-4b2e-ae09-23eb1b92bc26")
	private String id;

    @ApiModelProperty(value = "用户名", dataType = "String", example = "123")
    private String username;

    @ApiModelProperty(value = "昵称",dataType = "String", example = "小明")
    private String nickname;

    @ApiModelProperty(value = "头像", dataType = "String",example = "32bde70120d347fc9bdb12766e99be56")
    private String headUrl;

    @ApiModelProperty(value = "通讯地址", example = "")
    private String address;

    @ApiModelProperty(value = "等级", example = "0")
    private Short grade;

    @ApiModelProperty(value = "所在城市", example = "成都")
    private String city;

    @ApiModelProperty(value = "简介", example = "我是小明")
    private String summary;

    @ApiModelProperty(value = "生日", example = "2017-8-22")
    private Date birthday;

    @ApiModelProperty(value = "性别", example = "1男,2女")
    private Short sex;



}
