package com.anmong.core.vo.wap.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "历史消息")
public class WapUnreadMessageIndexVO {


    @ApiModelProperty(value = "头像", example = "http://www.baidu.com")
    private String headUrl;
    @ApiModelProperty(value = "消息内容", example = "123")
    private String content;
    @ApiModelProperty(value = "创建时间", example = "2018-01-01 00:00:00")
    private Date createAt;
}
