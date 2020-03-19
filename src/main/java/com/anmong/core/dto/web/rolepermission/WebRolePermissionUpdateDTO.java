package com.anmong.core.dto.web.rolepermission;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
@Data
@ApiModel
public class WebRolePermissionUpdateDTO {
	
	private List<String> addList = new ArrayList<>();
	
	private List<String> delList = new ArrayList<>();

	@ApiModelProperty(value = "roleid", example = "f80396300610471f971cc5ab18f716ab")
	@NotBlank(message="角色id不能为空")
	private String roleId;

	
	

}
