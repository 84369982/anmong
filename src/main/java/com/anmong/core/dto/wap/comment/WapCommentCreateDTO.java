package com.anmong.core.dto.wap.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author songwenlong
 * 2018/3/12
 */
@ApiModel(value = "添加评论信息")
@Data
public class WapCommentCreateDTO {

    @ApiModelProperty(value = "文章id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    @NotBlank(message="文章id不能为空")
    private String bizId;
    @ApiModelProperty(value = "评论类型：1文章评论,2针对评论的评论", example = "1")
    @NotNull(message="评论类型不能为空")
    private Short commentType;
    @ApiModelProperty(value = "目标评论id", example = "123")
    private String parentId;
    @ApiModelProperty(value = "内容类型：1图文,2音频", example = "1")
    @NotNull(message="内容类型不能为空")
    private Short contentType;
    @ApiModelProperty(value = "评论内容", example = "内容")
    private String content;
    @ApiModelProperty(value = "附件id",example = "1aec04c627c44b2eae0923eb1b92bc26")
    private String url;
    @ApiModelProperty(value = "用户id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    @NotBlank(message = "用户Id不能为空")
    private String createMan;

}
