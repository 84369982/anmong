package com.anmong.core.dto.web.file;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebFileIndexDTO extends BasePageDTO{

	@ApiModelProperty(value = "业务Id", example = "")
	private String bizId;

	@ApiModelProperty(value = "存储类型", example = "user")
	private String moduleCode;

	@ApiModelProperty(value = "存储类型", example = "1")
	private Integer storeType;

	@ApiModelProperty(value = "类型", example = "1,2,3")
	private Integer type;

	@ApiModelProperty(value = "是否存在业务id", example = "")
	private Integer hasBizId;


	

}
