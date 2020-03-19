package com.anmong.core.vo.wap.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author songwenlong
 * 2018/3/2
 */
@ApiModel(value = "热评列表")
@Data
public class WapHotCommentVO {

    @ApiModelProperty(value = "创建人",  example = "张三")
    private String createMan;
    @ApiModelProperty(value = "评论内容",  example = "真好")
    private String content;
    @ApiModelProperty(value = "评论类型:1图文，2.语音",  example = "真好")
    private Short contentType;
    @ApiModelProperty(value = "附件地址",  example = "http://www.baidu.com")
    private String url;

}
