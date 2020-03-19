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
import com.anmong.core.dto.web.userrole.WebUserRoleUpdateDTO;
import com.anmong.core.entity.User;
import com.anmong.core.service.web.WebUserRoleService;

@RestController
@RequestMapping("web/user-role")
public class WebUserRoleController {
	
	@Autowired
	private WebUserRoleService webUserRoleService;
	
	@RequestMapping(value = "find-all-allot-role",method = RequestMethod.GET)
	@ApiOperation(value = "查询所有可分配的角色", tags = "后台-用户_角色")
	public ModelAndView findAllAllotPermission(ModelAndView mv,@RequestParam String userId) {
        
		mv.addObject("list",JSONArray.toJSONString(webUserRoleService.findAllAllotRole()));
		mv.setViewName("user/user-allot-role");
		mv.addObject("userId",userId);
		mv.addObject("owned",JSONArray.toJSONString(webUserRoleService.findOwnedRole(userId)));
		return mv;
		
	}
	
	@PostMapping("allot-role")
	@ApiOperation(value = "分配角色", tags = "后台-用户_角色")
	public DosserReturnBody allotRole(@RequestBody @Valid WebUserRoleUpdateDTO updateDTO) {
		Subject currentUser = SecurityUtils.getSubject();
		AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
		webUserRoleService.updateAllotPermission(updateDTO, user.getId());
		return new DosserReturnBodyBuilder().statusOk().build();
		
	}
	


}
