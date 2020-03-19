package com.anmong.core.web.web;

import javax.validation.Valid;

import com.anmong.core.vo.web.user.AdminUserInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.web.rolepermission.WebRolePermissionUpdateDTO;
import com.anmong.core.entity.User;
import com.anmong.core.service.web.WebRolePermissionService;

@RestController
@RequestMapping("web/role-permission")
public class WebRolePermissionController {
	
	@Autowired
	private WebRolePermissionService webRolePermissionService;
	
	@RequestMapping(value = "find-all-allot-permission",method = RequestMethod.GET)
	@ApiOperation(value = "查询所有可分配的权限", tags = "后台-角色_权限")
	public ModelAndView findAllAllotPermission(ModelAndView mv,@RequestParam String roleId) {
        
		mv.addObject("list",JSONArray.toJSONString(webRolePermissionService.findAllAllotPermission()));
		mv.setViewName("role/role-allot-permission");
		mv.addObject("roleId",roleId);
		mv.addObject("owned",JSONArray.toJSONString(webRolePermissionService.findOwnedPermission(roleId)));
		return mv;
		
	}
	
	@PostMapping("allot-permission")
	@ApiOperation(value = "分配权限", tags = "后台-角色_权限")
	public DosserReturnBody allotPermission(@RequestBody @Valid WebRolePermissionUpdateDTO updateDTO) {
		Subject currentUser = SecurityUtils.getSubject();
		AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
		webRolePermissionService.updateAllotPermission(updateDTO, user.getId());
		return new DosserReturnBodyBuilder().statusOk().build();
		
	}
	

}
