package com.anmong.core.dto.wap.comment;

import com.anmong.common.pagination.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author songwenlong
 * 2018/3/2
 */
@ApiModel(value = "微博详情中查询转载列表")
@Data
public class WapCommentIndexDTO extends BasePageDTO{

    @ApiModelProperty(value = "微博id", example = "123")
    @NotBlank(message="微博id不能为空")
    private String articleId;
}
