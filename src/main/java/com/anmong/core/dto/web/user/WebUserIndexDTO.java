package com.anmong.core.dto.web.user;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebUserIndexDTO extends BasePageDTO{

	@ApiModelProperty(value = "用户名", example = "1324")
	private String username;

	@ApiModelProperty(value = "昵称", example = "小明")
	private String nickname;

	@ApiModelProperty(value = "类型", example = "1")
	private Integer type;



	

}
