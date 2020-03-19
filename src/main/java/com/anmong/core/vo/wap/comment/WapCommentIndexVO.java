package com.anmong.core.vo.wap.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author songwenlong
 * 2018/3/2
 */
@ApiModel(value = "微博详情评论列表")
@Data
public class WapCommentIndexVO {

    @ApiModelProperty(value = "评论id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    private String id;
    @ApiModelProperty(value = "创建人", dataType = "String", example = "张三")
    private String createMan;
    @ApiModelProperty(value = "创建时间",  example = "2018-01-01 00:00:00")
    private Date createAt;
    @ApiModelProperty(value = "点赞人数", example = "1")
    private Integer likeQuantity;
    @ApiModelProperty(value = "头像地址", dataType = "String", example = "http://www.baidu.com")
    private String headUrl;
    @ApiModelProperty(value = "评论内容:当状态为0时标红显示", dataType = "String", example = "真好")
    private String content;
    @ApiModelProperty(value = "评论类型:1图文，2.语音", dataType = "String", example = "真好")
    private Short contentType;
    @ApiModelProperty(value = "附件地址", dataType = "String", example = "http://www.baidu.com")
    private String url;
    @ApiModelProperty(value = "状态:0禁用1启用", example = "1")
    private Short state;
    @ApiModelProperty(value = "父评论列表")
    List<WapParentCommentIndexVO> parentCommentList;
    @JsonIgnore
    private String parentId;

}
