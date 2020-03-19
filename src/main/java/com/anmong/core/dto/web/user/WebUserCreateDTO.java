package com.anmong.core.dto.web.user;







import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.anmong.common.message.validate.PhoneNumber;
@Data
@ApiModel
public class WebUserCreateDTO {

	@ApiModelProperty(value = "用户名", example = "123")
	@NotBlank(message = "用户名不能为空!")
	@Length(min=6,max=16,message="用户名长度只能在6-16之间")
	private String username;

	@ApiModelProperty(value = "密码", example = "DDDDD44444")
	@NotBlank(message = "密码不能为空!")
	@Length(min=6,max=16,message="密码长度只能在6-16之间")
	private String password;

	@ApiModelProperty(value = "电话号码", example = "13618022466")
	@NotBlank(message = "电话号码不能为空!")
    @PhoneNumber
	private String phone;

	@ApiModelProperty(value = "验证码", example = "1324")
	@NotBlank(message = "验证码不能为空!")
	private String authcode;

	
	
	

}
