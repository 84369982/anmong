package com.anmong.common.constrant;

public enum HttpStatusCode {
	
	    STATUS_OK(Integer.valueOf(200), "success", "成功"),
	    STATUS_CREATED(Integer.valueOf(201), "success", "成功"),
	    STATUS_ACCEPTED(Integer.valueOf(202), "success", "成功"),
	    STATUS_NO_CONTENT(Integer.valueOf(204), "success", "成功"),
	    STATUS_BAD_REQUEST(Integer.valueOf(400), "failure-wrong-parameter", "输入信息有误"),
	    STATUS_UN_AUTHORIZED(Integer.valueOf(401), "failure", "请先登录"),
	    STATUS_FORBIDDEN(Integer.valueOf(403), "failure", "无权访问"),
	    STATUS_NOT_FOUND(Integer.valueOf(404), "failure-not-found", "没有找到符合条件的信息"),
	    STATUS_CONFLICT(Integer.valueOf(409), "failure-wrong-state", "资源冲突"),
	    STATUS_INTERNAL_SERVER_ERROR(Integer.valueOf(500), "failure", "服务器错误");

	    public Integer status;
	    public String code;
	    public String defaultMessage;

	    private HttpStatusCode(Integer status, String code, String defaultMessage) {
	        this.status = status;
	        this.code = code;
	        this.defaultMessage = defaultMessage;
	    }

}
