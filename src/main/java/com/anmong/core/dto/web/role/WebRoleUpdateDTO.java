package com.anmong.core.dto.web.role;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
@Data
@ApiModel(value="角色管理")
public class WebRoleUpdateDTO {
    @ApiModelProperty(value = " id", example = "3d471267190f11e8a46a00163e0cf26d")
	@NotBlank(message = "id不能为空!")
    private String id;

    @ApiModelProperty(value = " 角色名", example = "管理员")
	@NotBlank(message = "角色名不能为空!")
    private String name;

    @ApiModelProperty(value = " 角色编码", example = "admin：管理员，root:系统管理员")
	@NotBlank(message = "角色编码不能为空!")
    private String code;

    @ApiModelProperty(value = "记录", example = "普通管理员")
    private String note;

    @ApiModelProperty(value = " 状态", example = "1,0")
    @NotNull(message = "状态不能为空!")
    private Short state;


    

}
