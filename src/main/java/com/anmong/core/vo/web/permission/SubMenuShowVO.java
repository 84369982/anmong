package com.anmong.core.vo.web.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class SubMenuShowVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -607132827005412607L;

	@ApiModelProperty(value = "排序", example = "1")
	private Integer sort;

	@ApiModelProperty(value = "操作名称", example = "修改角色状态")
	private String name;

	@ApiModelProperty(value = "路径", example = "/web/role/change-state")
	private String url;

	

}
