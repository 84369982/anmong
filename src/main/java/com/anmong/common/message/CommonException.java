package com.anmong.common.message;

import java.util.HashMap;
import java.util.Map;

import com.anmong.common.constrant.HttpStatusCode;

public class CommonException extends RuntimeException {
    /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private HttpStatusCode status;
    private Object criteria = new Object();
    private Map<String, String> errorMap = new HashMap<>();

    private CommonException() {
        super("");
        this.status = HttpStatusCode.STATUS_BAD_REQUEST;
    }

    private CommonException(String message) {
        super(message);
        this.status = HttpStatusCode.STATUS_BAD_REQUEST;
    }

    public static CommonException unAuthorized(String message) {
        HttpStatusCode status = HttpStatusCode.STATUS_UN_AUTHORIZED;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        return commonException;
    }

    public static CommonException forbidden(String message) {
        HttpStatusCode status = HttpStatusCode.STATUS_FORBIDDEN;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        return commonException;
    }

    public static CommonException notFound(String message) {
        HttpStatusCode status = HttpStatusCode.STATUS_NOT_FOUND;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        return commonException;
    }

    public static CommonException conflict(String message) {
        HttpStatusCode status = HttpStatusCode.STATUS_CONFLICT;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        return commonException;
    }

    public static CommonException internalServerError(String message) {
        HttpStatusCode status = HttpStatusCode.STATUS_INTERNAL_SERVER_ERROR;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        return commonException;
    }

    public static CommonException badRequests(String message, Map<String, String> errorMap) {
        HttpStatusCode status = HttpStatusCode.STATUS_BAD_REQUEST;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        commonException.setErrorMap(errorMap);
        return commonException;
    }

    public static CommonException badRequests(String message) {
        HttpStatusCode status = HttpStatusCode.STATUS_BAD_REQUEST;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        return commonException;
    }

    public HttpStatusCode getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatusCode status) {
        this.status = status;
    }

    public Object getCriteria() {
        return this.criteria;
    }

    public void setCriteria(Object criteria) {
        this.criteria = criteria;
    }

    public Map<String, String> getErrorMap() {
        return this.errorMap;
    }

    public void setErrorMap(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }
    
    public static CommonException businessException(String message) {
        HttpStatusCode status = HttpStatusCode.STATUS_OK;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        return commonException;
    }
    
    public static CommonException notNull(Object object,String message) {
        HttpStatusCode status = HttpStatusCode.STATUS_OK;
        CommonException commonException = new CommonException(message);
        commonException.status = status;
        return commonException;
    }

}
