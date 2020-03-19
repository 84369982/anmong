package com.anmong.core.web.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anmong.core.vo.web.user.AdminUserInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.util.VerificationCodeUtils;
import com.anmong.core.enums.UserEnum;
import com.anmong.core.service.web.WebPermissionService;
import com.anmong.core.vo.web.permission.WebParentMenuIndexVO;
import com.anmong.core.vo.web.user.WebUserShowVO;

@RestController
@RequestMapping("web/auth")
public class WebAuthController {
	
	private static Logger log = LoggerFactory.getLogger(WebAuthController.class);
	
	@Autowired
	private WebPermissionService webPermissionService;
	

	
	@GetMapping("login")
	@ApiOperation(value = "登录", tags = "后台-登录")
	public DosserReturnBody login(HttpServletRequest request,@RequestParam String username
			,@RequestParam String password,@RequestParam String authcode) {
		if (!VerificationCodeUtils.checkVerificationCode(request,authcode)){
	            return new DosserReturnBodyBuilder().businessException("error").message("验证码不正确!").build();
	        }
		 //当前Subject    
        Subject currentUser = SecurityUtils.getSubject();    
        //传递token给shiro的realm  
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);    
        try {    
       	 if (currentUser.isAuthenticated()){
       		    //如果当期用户已登录，则清除登录信息，重新登录
       		    currentUser.logout();
                //使用shiro来验证  
                token.setRememberMe(false);  
                currentUser.login(token);//验证角色和权限  
            }else {
            	currentUser.login(token);//验证角色和权限  
            }  
            Session session = currentUser.getSession();
            AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
            if(UserEnum.State.禁用.code.intValue() == user.getState().intValue()) {
            	currentUser.logout();
            	return new DosserReturnBodyBuilder().businessException("error").message("账号已被封禁!").build();
	        }
            WebUserShowVO webUserShowVO = new WebUserShowVO();
            BeanUtils.copyProperties(user, webUserShowVO);
            session.setAttribute("user", webUserShowVO);
            //查询权限信息
            List<WebParentMenuIndexVO> webParentMenuIndexVOList = webPermissionService.findAllMenu(user.getId());
            session.setAttribute("menuList", webParentMenuIndexVOList);
            return new DosserReturnBodyBuilder().statusOk().build();
        } catch (AuthenticationException e) {//登录失败    
        	log.info("登陆失败:"+e.getMessage());
           return new DosserReturnBodyBuilder().businessException("error").message("用户名或密码错误!").build();
        }   
	}
        
        @GetMapping("logout")
		@ApiOperation(value = "注销登录", tags = "后台-注销")
    	public DosserReturnBody logout() {
	        Subject currentUser = SecurityUtils.getSubject();       
	        currentUser.logout();    
	        return new DosserReturnBodyBuilder().statusOk().build();
        }
}
