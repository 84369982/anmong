package com.anmong.core.dto.web.permission;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebPermissionIndexDTO extends BasePageDTO{

	@ApiModelProperty(value = "类型", example = "1：操作,2:菜单，3：后台首页")
	private Integer type;

	@ApiModelProperty(value = " 菜单名称", example = "更改文件状态")
	private String name;


	

}
