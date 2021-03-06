package com.anmong.core.dto.web.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel
public class WebFunctionPermissionCreateDTO {
	@ApiModelProperty(value = " 菜单名称", example = "更改文件状态")
	private String name;

	@ApiModelProperty(value = "类型", example = "1：操作,2:菜单，3：后台首页")
	private Short type;

	@ApiModelProperty(value = "菜单", example = "99847abcc51811e7910d00163e1c84ea")
	private String menuId;

	@ApiModelProperty(value = "记录", example = "")
	private String note;

	@ApiModelProperty(value = "地址", example = "/web/file/change-state")
	private String url;

	@ApiModelProperty(value = "加密", example = "")
	private String code;

	@ApiModelProperty(value = "是否根目录", example = "1::是,0:否")
	private Short isRoot;

	@ApiModelProperty(value = "状态", example = "1")
	private Short state;

	@ApiModelProperty(value = "创建人", example = "ab0eed4f9c2011e799de00163e1c84ea")
	private String createMan;

	@ApiModelProperty(value = "创建时间", example = "2017-11-09 14:41:22")
	private Date createAt;




}
