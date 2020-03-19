package com.anmong.core.dto.wap.article;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author songwenlong
 * 2018/3/2
 */
@ApiModel(value = "移动端查询文章列表")
@Data
public class WapMyBlogIndexDTO extends BasePageDTO{

    @ApiModelProperty(value = "用户id", example = "123")
    @NotBlank(message="用户id不允许为空")
    private String userId;
}
