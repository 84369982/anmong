package com.anmong.core.vo.wap.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author songwenlong
 * 2018/3/5
 */
@ApiModel(value = "转载列表信息")
@Data
public class WapForwardIndexVO {

    @ApiModelProperty(value = "用户id", example = "1aec04c627c44b2eae0923eb1b92bc26")
    private String userId;
    @ApiModelProperty(value = "创建人", example = "123")
    private String createMan;
    @ApiModelProperty(value = "时间", example = "2018-01-01 00:00:00")
    private Date createAt;
    @ApiModelProperty(value = "头像地址", example = "http://www.baidu.com")
    private String headUrl;
    @ApiModelProperty(value = "转载时评论", example = "内容")
    private String comment;


}
