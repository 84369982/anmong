package com.anmong.common.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anmong.core.vo.web.user.AdminUserInfo;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.util.WebUtil;

public class PermissionFilter extends PermissionsAuthorizationFilter{
	
	private static Logger log = LoggerFactory.getLogger(PermissionFilter.class);
	
/*	 @Override
	    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
	        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	        if (WebUtil.isAjaxRequest(httpServletRequest)) {  
	        	httpServletResponse.setStatus(200);
            	DosserReturnBody dosserReturnBody = new DosserReturnBody();
            	dosserReturnBody.setCode("401");
            	dosserReturnBody.setSuccess(false);
            	dosserReturnBody.setMessage("您尚未登录或登录时间过长,请重新登录!");
                WebUtil.sendJSON(httpServletResponse,dosserReturnBody);  
            } else {  
                saveRequestAndRedirectToLogin(request, response);  
            }  
	        return false;
	    }*/
	
	 protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {  
		  
	        HttpServletRequest httpRequest = (HttpServletRequest) request;  
	        HttpServletResponse httpResponse = (HttpServletResponse) response;  
	  
	        Subject subject = getSubject(request, response);  
	  
	        if (subject.getPrincipal() == null) {  
	            if (WebUtil.isAjaxRequest(httpRequest)) {  
	            	DosserReturnBody dosserReturnBody = new DosserReturnBody();
	            	dosserReturnBody.setCode("401");
	            	dosserReturnBody.setSuccess(false);
	            	dosserReturnBody.setMessage("您尚未登录或登录时间过长,请重新登录!");
	                WebUtil.sendJSON(httpResponse,dosserReturnBody);  
	            } else {  
	                saveRequestAndRedirectToLogin(request, response);  
	            }  
	        } else {  
	        	AdminUserInfo user = (AdminUserInfo) subject.getPrincipal();
	        	log.info("用户:"+user.getUsername()+"未授权访问:"+httpRequest.getRequestURI());
	            if (WebUtil.isAjaxRequest(httpRequest)) {  
	            	DosserReturnBody dosserReturnBody = new DosserReturnBody();
	            	dosserReturnBody.setCode("401");
	            	dosserReturnBody.setSuccess(false);
	            	dosserReturnBody.setMessage("您无权限操作!");
	                WebUtil.sendJSON(httpResponse,dosserReturnBody);  
	            } else {  
	                String unauthorizedUrl = getUnauthorizedUrl();  
	                if (StringUtils.hasText(unauthorizedUrl)) {  
	                    WebUtils.issueRedirect(request, response, unauthorizedUrl);  
	                } else {  
	                    WebUtils.toHttp(response).sendError(401);  
	                }  
	            }  
	        }  
	        return false;  
	    } 

	  public boolean isAccessAllowed(ServletRequest request,  
	            ServletResponse response, Object mappedValue) throws IOException {  
		     return super.isAccessAllowed(request, response, buildPermissions(request));
	        
	    }  
	    /** 
	     * 根据请求URL产生权限字符串，这里只产生，而比对的事交给Realm 
	     * @param request 
	     * @return 
	     */  
	    protected String[] buildPermissions(ServletRequest request) {  
	        String[] perms = new String[1];  
	        HttpServletRequest req = (HttpServletRequest) request;  
	        String path = req.getServletPath();  
	        perms[0] = path;//path直接作为权限字符串  
	        /*String regex = "/(.*?)/(.*?)\\.(.*)"; 
	        if(url.matches(regex)){ 
	            Pattern pattern = Pattern.compile(regex); 
	            Matcher matcher = pattern.matcher(url); 
	            String controller =  matcher.group(1); 
	            String action = matcher.group(2); 
	             
	        }*/  
	        return perms;  
	    }
	    
	    /**
	     * 判断ajax请求
	     * @param request
	     * @return
	     */
	    public boolean isAjax(HttpServletRequest request){
	        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
	    }
	    
	   

}
