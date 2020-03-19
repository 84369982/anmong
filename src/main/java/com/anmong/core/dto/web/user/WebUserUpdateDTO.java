package com.anmong.core.dto.web.user;






import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
@Data
@ApiModel
public class WebUserUpdateDTO {
	@ApiModelProperty(value = "用户id", example = "4db4706b9c3c11e799de00163e1c84ea")
	private String id;

	@ApiModelProperty(value = "昵称", example = "小明")
	private String nickname;

	@ApiModelProperty(value = "性别", example = "男")
	private Short sex;

	@ApiModelProperty(value = "简介", example = "我是小明")
	private String summary;

	@ApiModelProperty(value = "所在城市", example = "成都")
	private String city;

	@ApiModelProperty(value = "生日", example = "2017-8-22")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	@ApiModelProperty(value = "头像", example = "32bde70120d347fc9bdb12766e99be56")
	private String headUrl;

	@ApiModelProperty(value = "文件id", example = "")
	private String fileId;


	
	
	
	
	
	
	

}
