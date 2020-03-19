package com.anmong.core.service.web;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anmong.common.config.MyConfig;
import com.anmong.common.message.CommonException;
import com.anmong.common.pagination.DataTable;
import com.anmong.core.dto.web.role.WebRoleIndexDTO;
import com.anmong.core.entity.Role;


@Service
public class WebRoleService {

	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private WebRolePermissionService webRolePermissionService;
	@Autowired
	private WebUserRoleService webUserRoleService;
	
	public  DataTable findAll(HttpServletRequest request,WebRoleIndexDTO dto){
		DataTable dt = DataTable.getInstance(request, null);
		dto.setPageNO(dt.getStart()/dt.getLength() + 1);
		dto.setPageSize(dt.getLength());
		ResultPage<Role> page = roleDAO.findAllRole(dto);
		dt.setData(page.getContent());
		dt.setRecordsTotal(page.getTotalElements());
		dt.setRecordsFiltered(page.getTotalElements());
		return dt;
	}
	
    public void addRole(Role role) {
    	Role savedRole = roleDAO.selectOneWhere("code = ?",role.getCode());
    	if(savedRole != null) {
    		throw CommonException.businessException("该角色编码已存在!");
    	}
    	if(MyConfig.getConfig("system.role.rootCode").equals(role.getCode())) {
    		throw CommonException.businessException("不可创建该编码的角色!");
    	}
    	role.setCreateAt(new Date());
    	roleDAO.insert(role);
    }
    

    public void updateRole(Role role) {
		Role savedRole = roleDAO.selectOneWhere("code =? AND id !=?",role.getCode(),role.getId());
    	if(savedRole != null) {
    		throw CommonException.businessException("该角色编码已存在!");
    	}
    	if(MyConfig.getConfig("system.role.rootCode").equals(role.getCode())) {
    		throw CommonException.businessException("不可修改为该编码!");
    	}
    	roleDAO.updateSelective(role);
    }
    
    public Role findOneById(String id) {
    	return roleDAO.selectOneById(id);
    }
    
	public void delete(String id) {
		roleDAO.deleteOneById(id);
		webRolePermissionService.deleteByRoleId(id);
		webUserRoleService.deleteByRoleId(id);
	}
	
	public void changeState(String id,Short state) {
	 	roleDAO.updateSetWhereById("state = ?",id,state);
    }

	
}
