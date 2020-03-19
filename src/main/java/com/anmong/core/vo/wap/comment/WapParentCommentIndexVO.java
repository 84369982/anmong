package com.anmong.core.vo.wap.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author songwenlong
 * 2018/3/5
 */
@ApiModel(value = "父评论列表")
@Data
public class WapParentCommentIndexVO {

    @ApiModelProperty(value = "创建人",  example = "张三")
    private String createMan;
    @ApiModelProperty(value = "评论内容:当状态为0时标红显示",  example = "真好")
    private String content;
    @ApiModelProperty(value = "点赞人数", example = "1")
    private Integer likeQuantity;
    @ApiModelProperty(value = "评论类型:1图文，2.语音", example = "真好")
    private Short contentType;
    @ApiModelProperty(value = "附件地址",  example = "http://www.baidu.com")
    private String url;
    @ApiModelProperty(value = "状态:0禁用1启用", example = "1")
    private Short state;
    @JsonIgnore
    private String parentId;
}
