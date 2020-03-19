package com.anmong.core.dto.wap.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author songwenlong
 * 2018/2/27
 */
@ApiModel(value = "移动端发布文章")
@Data
public class WapArticleCreateDTO {

    @ApiModelProperty(value = "用户id", example = "123")
    @NotBlank(message = "用户Id不能为空")
    private String createMan;

    @ApiModelProperty(value = "文章内容", example = "内容")
    private String title;

    @ApiModelProperty(value = "文章内容", example = "内容")
    private String txt;

    @ApiModelProperty(value = "内容类型：1图文,2视频", example = "1")
    @NotNull(message="内容类型不能为空")
    private Short contentType;

    @ApiModelProperty(value = "附件id列表")
    private List<String> fileIdList = new ArrayList<>();

}
