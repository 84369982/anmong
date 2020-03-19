package com.anmong.common.message;

import com.alibaba.fastjson.JSONObject;
import com.anmong.common.constrant.HttpStatusCode;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//@ControllerAdvice
@RestControllerAdvice
public class ExceptionController {
    private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    public ExceptionController() {
    }

    @ExceptionHandler
    public DosserReturnBody errorMessage(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof CommonException) {
            logger.info("CommonException:-----" + ex.getClass().getSimpleName() + "-" + ex.getMessage());
            CommonException commonEx = (CommonException)ex;
            Map<String, List<String>> errors = this.doWithErrors(commonEx.getErrorMap());
            return commonEx.getMessage().equals("") ? (new DosserReturnBodyBuilder()).success(false).status(commonEx.getStatus()).errors(errors).build() : (new DosserReturnBodyBuilder()).success(false).status(commonEx.getStatus()).message(commonEx.getMessage()).errors(errors).build();
        } else {
            List errorList;
            String bindExceptionStr;
            if (ex instanceof BindException) {
                errorList = ((BindException)ex).getFieldErrors();
                bindExceptionStr = this.getBindExceptionStr(errorList);
                logger.error("参数异常:{}",JSONObject.toJSON(this.getErrorMapFromFieldError(errorList)).toString());
                return (new DosserReturnBodyBuilder()).success(false).statusBadRequest().message(bindExceptionStr).errors(this.getErrorMapFromFieldError(errorList)).build();
            } else if (ex instanceof MethodArgumentNotValidException) {
                errorList = ((MethodArgumentNotValidException)ex).getBindingResult().getFieldErrors();
                bindExceptionStr = this.getBindExceptionStr(errorList);
                return (new DosserReturnBodyBuilder()).success(false).statusBadRequest().message(bindExceptionStr).errors(this.getErrorMapFromFieldError(errorList)).build();
            } else if (ex instanceof HttpMessageNotReadableException) {
                return (new DosserReturnBodyBuilder()).success(false).statusInternalServerError().message("请求体的格式错误").build();
            } else if (ex instanceof MissingServletRequestParameterException) {
                MissingServletRequestParameterException missEx = (MissingServletRequestParameterException)ex;
                Map<String, List<String>> errMap = new HashMap();
                logger.error("参数{}异常",missEx.getParameterName());
                errMap.put(missEx.getParameterName(), Lists.newArrayList(new String[]{"不允许为空"}));
                return (new DosserReturnBodyBuilder()).success(false).statusBadRequest().errors(errMap).build();
            } else {
            	ex.printStackTrace();
                logger.error("exception:-----业务逻辑异常#" + ex.getClass().getSimpleName() + "#" + ex.getMessage());
                return (new DosserReturnBodyBuilder()).success(false).statusInternalServerError().message("业务逻辑异常#").build();
                /*return (new DosserReturnBodyBuilder()).success(false).statusInternalServerError().message("业务逻辑异常#" + ex.getClass().getSimpleName() + "#" + ex.getMessage()).build();*/
            }
        }
    }

    private Map<String, List<String>> doWithErrors(Map<String, String> map) {
        Map<String, List<String>> errorMap = new HashMap();
        Iterator var3 = map.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, String> entry = (Entry)var3.next();
            errorMap.put(entry.getKey(), Lists.newArrayList(new String[]{(String)entry.getValue()}));
        }

        return errorMap;
    }

    private String getBindExceptionStr(List<FieldError> errorList) {
        StringBuilder builder = new StringBuilder("");
        Iterator var3 = errorList.iterator();

        while(var3.hasNext()) {
            FieldError error = (FieldError)var3.next();
            String defaultMessage = error.getDefaultMessage();
            if (defaultMessage != null && defaultMessage.contains("#")) {
                builder.append("， ").append(defaultMessage.replace("#", ""));
            }
        }

        String msg = builder.toString().replaceFirst("， ", "");
        if (StringUtils.isEmpty(msg)) {
            return HttpStatusCode.STATUS_BAD_REQUEST.defaultMessage;
        } else {
            return msg;
        }
    }

    private Map<String, List<String>> getErrorMapFromFieldError(List<FieldError> errorList) {
        Map<String, List<String>> errorMap = new HashMap();

        FieldError fieldError;
        String defaultMessage;
        for(Iterator var3 = errorList.iterator(); var3.hasNext(); errorMap.put(fieldError.getField(), Lists.newArrayList(new String[]{defaultMessage}))) {
            fieldError = (FieldError)var3.next();
            defaultMessage = fieldError.getDefaultMessage();
            if (!StringUtils.isEmpty(defaultMessage) && defaultMessage.contains("#")) {
                String[] split = defaultMessage.split("#");
                if (split.length > 1) {
                    defaultMessage = split[1];
                }
            }
        }

        return errorMap;
    }
}
