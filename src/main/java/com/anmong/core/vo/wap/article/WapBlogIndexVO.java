package com.anmong.core.vo.wap.article;

import com.anmong.core.vo.wap.comment.WapHotCommentVO;
import com.anmong.core.vo.wap.file.FileIndexVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author songwenlong
 * 2018/3/2
 */
@Data
@ApiModel(value = "微博列表")
public class WapBlogIndexVO {

    @ApiModelProperty(value = "32位UUID", dataType = "String", example = "1aec04c627c44b2eae0923eb1b92bc26")
    private String id;
    @ApiModelProperty(value = "标题", dataType = "String", example = "标题")
    private String title;
    @ApiModelProperty(value = "文本内容", dataType = "String", example = "内容")
    private String txt;
    @ApiModelProperty(value = "文章类型:1.微博2.自媒体 3.广告", dataType = "Integer", example = "1")
    private Short articleType;
    @ApiModelProperty(value = "内容类型:1.图文2.视频", dataType = "Integer", example = "1")
    private Short contentType;
    @ApiModelProperty(value = "点击量", dataType = "Integer", example = "1")
    private Integer readQuantity;
    @ApiModelProperty(value = "转发量", dataType = "Integer", example = "1")
    private Integer forwardQuantity;
    @ApiModelProperty(value = "点赞量", dataType = "Integer", example = "1")
    private Integer likeQuantity;
    @ApiModelProperty(value = "评论量", dataType = "Integer", example = "1")
    private Integer commentQuantity;
    @ApiModelProperty(value = "是否为转载的文章:0否1是", dataType = "Integer", example = "1")
    private Short isForward;
    @ApiModelProperty(value = "来源id", dataType = "String", example = "1aec04c627c44b2eae0923eb1b92bc26")
    private String sourceId;
    @ApiModelProperty(value = "创建人", dataType = "String", example = "张三")
    private String createMan;
    @ApiModelProperty(value = "创建人id", dataType = "String", example = "1aec04c627c44b2eae0923eb1b92bc26")
    private String userId;
    @ApiModelProperty(value = "时间", dataType = "Date", example = "2018-01-01 00:00:00")
    private Date createAt;
    @ApiModelProperty(value = "头像地址", dataType = "String", example = "http://www.baidu.com")
    private String headUrl;
    @ApiModelProperty(value = "转载信息")
    private WapForwardBlogVO forwardBlog;
    @ApiModelProperty(value = "热评")
    private List<WapHotCommentVO> commentList;
    @ApiModelProperty(value = "附件列表")
    private List<FileIndexVO> fileList;
}
