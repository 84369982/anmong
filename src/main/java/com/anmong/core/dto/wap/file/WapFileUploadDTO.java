package com.anmong.core.dto.wap.file;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
@Data
@ApiModel
public class WapFileUploadDTO {

	@ApiModelProperty(value = "模块代码:用户模块(user),微博(blog)", example = "user")
	@NotBlank(message="所属模块不能为空")
	private String module;

	@ApiModelProperty(value = "用户id", example = "4db4706b9c3c11e799de00163e1c84ea")
	@NotBlank(message="用户id不能为空")
	private String userId;

	@ApiModelProperty(value = "类型:图片(1),视频(2),音频(3)", example = "1,2,3")
	@NotNull(message="上传类型不能为空")
	private Short type;

	@ApiModelProperty(value = "名称", example = "测试上传")
	private String name;

	
	
	

}
