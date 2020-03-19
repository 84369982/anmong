package com.anmong.core.dto.web.file;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
@Data
@ApiModel
public class WebFileUploadDTO {
	@ApiModelProperty(value = "模块", example = "用户模块")
	@NotBlank(message="所属模块不能为空")
	private String module;

	@ApiModelProperty(value = "用户id", example = "4db4706b9c3c11e799de00163e1c84ea")
	@NotBlank(message="用户id不能为空")
	private String userId;

	@ApiModelProperty(value = "类型", example = "1")
	@NotNull(message="上传类型不能为空")
	private Integer type;

	
	

}
