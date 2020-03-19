package com.anmong.core.web.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.anmong.core.vo.web.user.AdminUserInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.pagination.DataTable;
import com.anmong.core.dto.wap.user.WapUserCreateDTO;
import com.anmong.core.dto.wap.user.WapUserUpdateDTO;
import com.anmong.core.dto.web.user.WebUserChangePasswordDTO;
import com.anmong.core.dto.web.user.WebUserCreateDTO;
import com.anmong.core.dto.web.user.WebUserIndexDTO;
import com.anmong.core.dto.web.user.WebUserUpdateDTO;
import com.anmong.core.entity.Role;
import com.anmong.core.entity.User;
import com.anmong.core.service.wap.WapUserService;
import com.anmong.core.service.web.WebUserService;
import com.anmong.core.vo.web.user.WebUserShowVO;

@RestController
@RequestMapping("web/user")
public class WebUserController {
	
	@Autowired
	private WebUserService webUserService;
	
	@PostMapping("find-all")
	@ApiOperation(value = "查询用户列表", tags = "后台-用户")
	public DataTable findAll(HttpServletRequest request,@Valid WebUserIndexDTO indexDTO) {
		DataTable dt = webUserService.findAll(request,indexDTO);
		return dt;
	}
	
	@PostMapping("change-state")
	@ApiOperation(value = "改变用户状态", tags = "后台-用户")
	public DosserReturnBody update(@RequestParam String id,@RequestParam Short state) {
		User entity = new User();
		entity.setId(id);
		entity.setState(state);
		webUserService.changeState(entity);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@PostMapping("add")
	@ApiOperation(value = "添加用户", tags = "后台-用户")
	public DosserReturnBody register(@Valid WebUserCreateDTO createDTO) {
		Subject currentUser = SecurityUtils.getSubject();
		AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
		webUserService.addUser(createDTO,user.getId());
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@RequestMapping(value = "find-one",method = RequestMethod.GET)
	@ApiOperation(value = "查询用户详情", tags = "后台-用户")
	public ModelAndView findOne(ModelAndView mv) {
		Subject currentUser = SecurityUtils.getSubject();
		AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
		mv.addObject("entity",webUserService.findOne(user.getId()));
		mv.setViewName("user/user-edit");
		return mv;
	}
	
	@PostMapping("update")
	@ApiOperation(value = "更改用户信息", tags = "后台-用户")
	public DosserReturnBody changeUserInfo( @Valid WebUserUpdateDTO updateDTO) {
		Subject currentUser = SecurityUtils.getSubject();
		AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
		updateDTO.setId(user.getId());
		webUserService.updateUserInfo(updateDTO);
		Session session = currentUser.getSession();
		WebUserShowVO webUserShowVO = new WebUserShowVO();
		webUserShowVO.setId(user.getId());
		webUserShowVO.setUsername(user.getUsername());
		webUserShowVO.setNickname(updateDTO.getNickname());
		if(!StringUtils.isEmpty(updateDTO.getFileId())) {
			webUserShowVO.setHeadUrl(updateDTO.getHeadUrl());
		}
		session.removeAttribute("user");
		session.setAttribute("user", webUserShowVO);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@PostMapping("change-password")
	@ApiOperation(value = "修改密码", tags = "后台-用户")
	public DosserReturnBody changePassword(@Valid WebUserChangePasswordDTO updateDTO) {
		Subject currentUser = SecurityUtils.getSubject();
		AdminUserInfo user = (AdminUserInfo)currentUser.getPrincipal();
		webUserService.changePassword(updateDTO, user.getId());
		currentUser.logout();
		return new DosserReturnBodyBuilder().statusOk().build();
	}

}
