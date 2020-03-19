package com.anmong.core.web.web;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.anmong.core.vo.web.user.AdminUserInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.pagination.DataTable;
import com.anmong.core.dto.web.permission.WebPermissionIndexDTO;
import com.anmong.core.entity.Permission;
import com.anmong.core.entity.User;
import com.anmong.core.service.web.WebPermissionService;


@RestController
@RequestMapping("web/permission")
public class WebPermissionController {
	
	@Autowired
	private WebPermissionService webPermissionService;
	
	@PostMapping("find-all")
	@ApiOperation(value = "查询选项列表", tags = "后台-权限")
	public DataTable findAll(HttpServletRequest request,@Valid WebPermissionIndexDTO indexDTO) {
		DataTable dt = webPermissionService.findAll(request,indexDTO);
		return dt;
	}
	
	@GetMapping("find-all-submenu")
	@ApiOperation(value = "查询所有子菜单", tags = "后台-权限")
	public DosserReturnBody findAllSubmenu() {
		  return new DosserReturnBodyBuilder().collection(webPermissionService.findAllSubmenu()).build();
	  
	}
	
	@GetMapping("find-all-topmenu")
	@ApiOperation(value = "查询所有顶级菜单", tags = "后台-权限")
	public DosserReturnBody findAllTopmenu() {
		  return new DosserReturnBodyBuilder().collection(webPermissionService.findAllTopmenu()).build();
	  
	}
	
	@PostMapping("add")
	@ApiOperation(value = "添加权限资源", tags = "后台-权限")
	public DosserReturnBody add(Permission permission) {
		Subject currentUser = SecurityUtils.getSubject();
		AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
		permission.setCreateMan(user.getId());
		webPermissionService.addPermission(permission);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@RequestMapping(value = "find-one",method = RequestMethod.GET)
	@ApiOperation(value = "查询权限详情", tags = "后台-权限")
	public ModelAndView findOne(ModelAndView mv,@RequestParam String id) {
		mv.addObject("entity",webPermissionService.findOne(id));
		mv.setViewName("permission/permission-edit");
		return mv;
	}
	
	@PostMapping("update")
	@ApiOperation(value = "更新权限资源", tags = "后台-权限")
	public DosserReturnBody update(Permission permission) {
		webPermissionService.updatePermission(permission);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@PostMapping("delete")
	@ApiOperation(value = "删除权限资源", tags = "后台-权限")
	public DosserReturnBody delete(@RequestParam String id) {
		webPermissionService.delete(id);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	

}
