package com.anmong.core.vo.wap.article;

import com.anmong.core.vo.wap.file.FileIndexVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author songwenlong
 * 2018/3/2
 */
@ApiModel(value = "转载信息")
@Data
public class WapForwardBlogVO {

    @ApiModelProperty(value = "32位UUID", dataType = "String", example = "1aec04c627c44b2eae0923eb1b92bc26")
    private String id;
    @ApiModelProperty(value = "标题", dataType = "String", example = "标题")
    private String title;
    @ApiModelProperty(value = "标题图:类型为自媒体时此字段不为空", example = "http://www.baidu.com")
    private String titleImg;
    @ApiModelProperty(value = "转载时的评论", dataType = "String", example = "内容")
    private String comment;
    @ApiModelProperty(value = "文章类型:1.微博2.自媒体 3.广告", dataType = "Integer", example = "1")
    private Short articleType;
    @ApiModelProperty(value = "内容类型:1.图文2.视频", dataType = "Integer", example = "1")
    private Short contentType;
    @ApiModelProperty(value = "创建人", dataType = "Integer", example = "1")
    private String createMan;
    @ApiModelProperty(value = "头像地址", dataType = "String", example = "http://www.baidu.com")
    private String headUrl;
    @ApiModelProperty(value = "附件列表")
    private List<FileIndexVO> fileList;
}
