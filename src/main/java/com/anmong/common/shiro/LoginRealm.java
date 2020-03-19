package com.anmong.common.shiro;

import java.util.ArrayList;
import java.util.List;

import com.anmong.core.dao.PermissionDAO;
import com.anmong.core.dao.RoleDAO;
import com.anmong.core.dao.UserDAO;
import com.anmong.core.vo.web.user.AdminUserInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


public class LoginRealm extends AuthorizingRealm {
	    private static Logger logger = LoggerFactory.getLogger(LoginRealm.class);
	    private static final String ALGORITHM = "MD5";  


	    @Autowired
		private UserDAO userDAO;
	    @Autowired
		private PermissionDAO permissionDAO;
	    @Autowired
        private RoleDAO roleDAO;
	    
	  
	    public LoginRealm() {  
	        super();  
	    }  
	      
	    /** 
	     * 验证登陆 
	     */  
	    @Override  
	    protected AuthenticationInfo doGetAuthenticationInfo(  
	            AuthenticationToken authcToken) throws AuthenticationException {  
	        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
	        System.out.println(token.getUsername());  
	        
	     // token中包含用户输入的用户名和密码  
	        // 第一步从token中取出用户名  
	       // String userName = (String) token.getPrincipal();  
	        // 第二步：根据用户输入的userCode从数据库查询  
	        AdminUserInfo user = userDAO.findAdminUserInfoByUsername(token.getUsername());
	        // 如果查询不到返回null  
	        if (user == null) {//  
	            throw new AuthenticationException("用户名或密码错误!");  
	        }  
	        // 获取数据库中的密码  
	        String password = user.getPassword();  
	        /** 
	         * 认证的用户,正确的密码 
	         */  
	        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, password, this.getName());  
	               //MD5 加密+加盐+多次加密  
	//<span style="color:#ff0000;">SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(adminUser, password,ByteSource.Util.bytes(salt), this.getName());</span>  
	        return authcInfo;  
	    }  
	  
	    /** 
	     * 登陆成功之后，进行角色和权限验证 
	     */  
	    @Override  
	    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
	    	 // 从 principals获取主身份信息  
	        // 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），  
			AdminUserInfo activeUser = (AdminUserInfo) principals.getPrimaryPrincipal();
	        // 根据身份信息获取权限信息  
	    	List<String> roleList = roleDAO.findAllRoleCodeByUserId(activeUser.getId());
	    	List<String> roles = new ArrayList<String>();  
		    if (roleList != null && !roleList.isEmpty() && roleList.size() > 0) {  
		        for(String roleCode: roleList) {
		        	roles.add(roleCode);
		        	}
		        }  
	        // 从数据库获取到权限数据  
	        List<String> permissionList = permissionDAO.findAllpermissionUrlByUserId(activeUser.getId());
	        // 单独定一个集合对象  
	        List<String> permissions = new ArrayList<String>();  
	        if (permissionList != null && !permissionList.isEmpty()) {
	        	for(String permission: permissionList) {
	        		//一级菜单和按钮不需要验权
	        		if(!StringUtils.isEmpty(permission)) {
	        			 permissions.add(permission);
	        		}
	        		
	        	}
	           
	        }  
	        // 查到权限数据，返回授权信息(要包括 上边的permissions)  
	        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();  
	        // 将上边查询到授权信息填充到simpleAuthorizationInfo对象中  
	        simpleAuthorizationInfo.addRoles(roles);
	        simpleAuthorizationInfo.addStringPermissions(permissions);  
	        
	        return simpleAuthorizationInfo;  
	    }  
	  
	  
	    /** 
	     * 清除所有用户授权信息缓存. 
	     */  
	    public void clearCachedAuthorizationInfo(String principal) {  
	        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());  
	        clearCachedAuthorizationInfo(principals);  
	    }  
	  
	  
	    /** 
	     * 清除所有用户授权信息缓存. 
	     */  
	    public void clearAllCachedAuthorizationInfo() {  
	        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();  
	        if (cache != null) {  
	            for (Object key : cache.keys()) {  
	                cache.remove(key);  
	            }  
	        }  
	    }  

}
