package com.anmong.core.vo.web.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class WebParentMenuIndexVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8974697012645092518L;

	@ApiModelProperty(value = "图标", example = "&#xe62e;")
	private String icon;

	@ApiModelProperty(value = "操作名称", example = "修改角色状态")
	private String name;
	
	private List<SubMenuShowVO> subMenuList;

	@ApiModelProperty(value = "排序", example = "1")
	private Integer sort;


}
