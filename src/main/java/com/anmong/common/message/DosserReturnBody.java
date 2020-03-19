package com.anmong.common.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DosserReturnBody {
   /* @ApiModelProperty(
        value = "请求是否成功",
        dataType = "boolean"
    )
    @JSONField(
        ordinal = 1
    )*/
    private boolean success = true;
   /* @ApiModelProperty(
        value = "响应描述",
        dataType = "String"
    )
    @JSONField(
        ordinal = 2
    )*/
    private String code;
   /* @ApiModelProperty(
        value = "响应信息",
        dataType = "String"
    )
    @JSONField(
        ordinal = 3
    )*/
    private String message;
   /* @ApiModelProperty(
        value = "响应资源集合",
        dataType = "List<Object>"
    )
    @JSONField(
        ordinal = 4
    )*/
    private List<Object> collection = new ArrayList<>();
   /* @ApiModelProperty(
        value = "'collection'内元素的数量",
        dataType = "int"
    )
    @JSONField(
        ordinal = 5
    )*/
    private int size = 0;
 /*   @ApiModelProperty(
        value = "错误信息",
        dataType = "Map<String, List<String>>"
    )
    @JSONField(
        ordinal = 6
    )*/
    private Map<String, List<String>> errors = new HashMap<>();
/*    @ApiModelProperty(
        value = "元信息，包含uuid和时间戳",
        dataType = "Map<String, Object>"
    )
    @JSONField(
        ordinal = 7
    )*/
    private Map<String, Object> meta = new HashMap<>();

    public DosserReturnBody() {
        this.meta.put("timestamp", System.currentTimeMillis());
        this.meta.put("requestId", UUID.randomUUID().toString());
    }

    public void addCriteria(Object criteria) {
        this.meta.put("criteria", criteria);
    }

    public void addPagination(Map<String, Object> pagination) {
        this.meta.put("pagination", pagination);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getCollection() {
        return this.collection;
    }

    public void setCollection(List<Object> collection) {
        this.collection = collection;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String, List<String>> getErrors() {
        return this.errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public Map<String, Object> getMeta() {
        return this.meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }
}
