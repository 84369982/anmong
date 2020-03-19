package com.anmong.core.dto.wap.user;






import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
@Data
@ApiModel
public class WapUserUpdateDTO {


	@NotBlank(message = "id不能为空!")
	private String id;

	@ApiModelProperty(value = "昵称", example = "小明")
	private String nickname;

	@ApiModelProperty(value = "性别", example = "男")
	private Short sex;

	@ApiModelProperty(value = "简介", example = "大家好，我是张三1")
	private String summary;

	@ApiModelProperty(value = "所在城市", example = "成都")
	private String city;

	@ApiModelProperty(value = "生日", example = "2017-08-02 00:00:00")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	
	

}
