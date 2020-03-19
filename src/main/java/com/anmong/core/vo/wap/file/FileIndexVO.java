package com.anmong.core.vo.wap.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author songwenlong
 * 2018/3/2
 */
@ApiModel(value = "附件列表")
@Data
public class FileIndexVO {

    @ApiModelProperty(value = "附件类型:1.图文2.视频", dataType = "Integer", example = "1")
    private Short type;
    @ApiModelProperty(value = "访问地址", dataType = "String", example = "http://www.baidu.com")
    private String url;



}
