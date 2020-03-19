package com.anmong.core.dto.web.userrole;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
@Data
public class WebUserRoleUpdateDTO {
	
	private List<String> addList = new ArrayList<>();
	
	private List<String> delList = new ArrayList<>();

	@ApiModelProperty(value = "用户id", example = "4db4706b9c3c11e799de00163e1c84ea")
	@NotBlank(message="用户id不能为空")
	private String userId;


}
