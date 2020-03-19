package com.anmong.core.vo.web.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class WebUserShowVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6373441326864359727L;

	@ApiModelProperty(value = "36位UUID", example = "1aec04c6-27c4-4b2e-ae09-23eb1b92bc26")
	private String id;

	@ApiModelProperty(value = "用户名", dataType = "String", example = "123")
    private String username;

	@ApiModelProperty(value = "昵称", dataType = "String", example = "123")
    private String nickname;

	@ApiModelProperty(value = "头像", dataType = "String",example = "32bde70120d347fc9bdb12766e99be56")
    private String headUrl;


    
    

}
