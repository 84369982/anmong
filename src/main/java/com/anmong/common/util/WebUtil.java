package com.anmong.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class WebUtil {
	
	private static Logger log = LoggerFactory.getLogger(WebUtil.class);
	
	 /**
     * 判断ajax请求
     * @param request
     * @return
     */
    public static boolean  isAjaxRequest(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
    }
    
    public static void sendJSON(HttpServletResponse response,Object object) {
    	 response.setCharacterEncoding("UTF-8");  
    	 response.setContentType("application/json; charset=utf-8");
    	 try {
			response.getWriter().write(JSONObject.toJSONString(object));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("输出json失败:"+e.getMessage());
		}
  
    }


}
