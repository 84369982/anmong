package com.anmong.core.dto.wap.attention;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author songwenlong
 * 2018/3/12
 */
@ApiModel(value = "关注/粉丝 列表")
@Data
public class WapAttentionIndexDTO extends BasePageDTO{

    @ApiModelProperty(value = "用户id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    @NotBlank(message="用户id不能为空")
    private String userId;


}
