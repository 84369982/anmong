package com.anmong.core.dto.wap.article;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author songwenlong
 * 2018/3/2
 */
@ApiModel(value = "移动端查询文章列表")
@Data
public class WapBlogIndexDTO extends BasePageDTO{

    @ApiModelProperty(value = "类型：1最热,2最新", example = "1")
    @NotNull(message="类型不能为空")
    private Integer type;
}
