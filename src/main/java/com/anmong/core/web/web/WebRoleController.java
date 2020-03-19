package com.anmong.core.web.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.anmong.core.vo.web.user.AdminUserInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.pagination.DataTable;
import com.anmong.core.dto.web.role.WebRoleCreateDTO;
import com.anmong.core.dto.web.role.WebRoleIndexDTO;
import com.anmong.core.dto.web.role.WebRoleUpdateDTO;
import com.anmong.core.entity.Role;
import com.anmong.core.entity.User;
import com.anmong.core.service.web.WebRoleService;

@RestController
@RequestMapping("web/role")
public class WebRoleController {
	
	@Autowired
	private WebRoleService webRoleService;
	
	@PostMapping("find-all")
	@ApiOperation(value = "查询角色列表", tags = "后台-角色")
	public DataTable findAll(HttpServletRequest request,@Valid WebRoleIndexDTO indexDTO) {
		 return webRoleService.findAll(request,indexDTO);
	}
	
	@PostMapping("add")
	@ApiOperation(value = "添加角色", tags = "后台-角色")
	public DosserReturnBody add(@Valid WebRoleCreateDTO createDTO) {
		Role role = new Role();
		BeanUtils.copyProperties(createDTO, role);
		Subject currentUser = SecurityUtils.getSubject();
		AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
		role.setCreateMan(user.getId());
		webRoleService.addRole(role);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@PostMapping("update")
	@ApiOperation(value = "更新角色", tags = "后台-角色")
	public DosserReturnBody update(@Valid WebRoleUpdateDTO updateDTO) {
		Role role = new Role();
		BeanUtils.copyProperties(updateDTO, role);
		webRoleService.updateRole(role);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@PostMapping("change-state")
	@ApiOperation(value = "改变角色状态", tags = "后台-角色")
	public DosserReturnBody update(@RequestParam String id,@RequestParam Short state) {
		webRoleService.changeState(id,state);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@PostMapping("delete")
	@ApiOperation(value = "删除角色", tags = "后台-角色")
	public DosserReturnBody delete(@RequestParam String id) {
		webRoleService.delete(id);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@RequestMapping(value = "find-one",method = RequestMethod.GET)
	@ApiOperation(value = "查询角色详情", tags = "后台-角色")
	public ModelAndView findOne(ModelAndView mv,@RequestParam String id) {
		mv.addObject("entity",webRoleService.findOneById(id));
		mv.setViewName("role/role-edit");
		return mv;
	}
	

}
