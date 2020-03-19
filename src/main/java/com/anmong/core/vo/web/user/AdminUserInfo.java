package com.anmong.core.vo.web.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author songwenlong
 * 2018/2/24
 */
@Data
@ApiModel
public class AdminUserInfo implements Serializable{

    @ApiModelProperty(value = "36位UUID", example = "1aec04c6-27c4-4b2e-ae09-23eb1b92bc26")
    private String id;

    @ApiModelProperty(value = "用户名", example = "123")
    private String username;

    @ApiModelProperty(value = "密码", example = "")
    private String password;

    @ApiModelProperty(value = "电话号码", example = "13618022466")
    private String phone;

    @ApiModelProperty(value = "性别", example = "男")
    private Short sex;

    @ApiModelProperty(value = "昵称", example = "小明")
    private String nickname;

    @ApiModelProperty(value = "头像", example = "32bde70120d347fc9bdb12766e99be56")
    private String headUrl;

    @ApiModelProperty(value = "所在城市", example = "成都")
    private String city;

    @ApiModelProperty(value = "上次登录", example = "")
    private Date lastLogin;

    @ApiModelProperty(value = "上次登录时间", example = "2017-09-18 06:40:47")
    private Integer loginTimes;

    @ApiModelProperty(value = "类型", example = "1")
    private Short type;

    @ApiModelProperty(value = "等级", example = "1")
    private Short grade;

    @ApiModelProperty(value = "生日", example = "2017-8-22")
    private Date birthday;

    @ApiModelProperty(value = "简介", example = "我是小明")
    private String summary;

    @ApiModelProperty(value = "状态", example = "1")
    private Short state;

    @ApiModelProperty(value = "创建人", example = "ab0eed4f9c2011e799de00163e1c84ea")
    private String createMan;

    @ApiModelProperty(value = "创建时间", example = "2017-11-09 14:41:22")
    private Date createAt;

}
