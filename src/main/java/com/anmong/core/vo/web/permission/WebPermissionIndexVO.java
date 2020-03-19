package com.anmong.core.vo.web.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel
public class WebPermissionIndexVO {

    @ApiModelProperty(value = "36位UUID", example = "1aec04c6-27c4-4b2e-ae09-23eb1b92bc26")
    private String id;

    @ApiModelProperty(value = "父菜单id", example = "1aec04c6-27c4-4b2e-ae09-23eb1b92bc26")
    private String parentId;

    @ApiModelProperty(value = "操作名称", example = "修改角色状态")
    private String name;

    @ApiModelProperty(value = "操作类型", example = "1:具体操作（修改密码）,2:管理栏（文件管理），3:后台首页")
    private Short type;

    @ApiModelProperty(value = "图标", example = "&#xe62e;")
    private String icon;

    @ApiModelProperty(value = "路径", example = "/web/role/change-state")
    private String url;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "加密", example = "")
    private String code;

    @ApiModelProperty(value = "状态", example = "1")
    private Short state;

    @ApiModelProperty(value = "创建人", example = "ab0eed4f9c2011e799de00163e1c84ea")
    private String createMan;

    @ApiModelProperty(value = "创建时间", example = "2017-11-09 14:41:22")
    private Date createAt;

}
