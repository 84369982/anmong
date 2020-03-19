package com.anmong.core.service.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.anmong.common.fastsql.util.FastSqlUtils;
import com.anmong.core.dao.RoleDAO;
import com.anmong.core.dao.UserRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anmong.common.config.MyConfig;
import com.anmong.common.util.RandomUtil;
import com.anmong.core.dto.web.userrole.WebUserRoleUpdateDTO;
import com.anmong.core.entity.Role;
import com.anmong.core.entity.UserRole;
import com.anmong.core.enums.CommonEnum;

@Service
public class WebUserRoleService {

	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	
	/**
	 * 查询所有可分配的角色
	 */
	public List<Role> findAllAllotRole() {
		return roleDAO.getSQL()
				.SELECT_all_FROM("roles")
				.WHERE("state").eqByType(CommonEnum.State.启用.code)
				.ifTrueAND("code != ",MyConfig.getConfig("system.role.rootCode"),!Boolean.parseBoolean(MyConfig.getConfig("system.run.isDebug")))
				.queryList(Role.class);
	}
	
	public List<UserRole> findOwnedRole(String userId){
		return userRoleDAO.selectWhere("user_id = ?",userId);
	}
	
	/**
	 * 分配权限
	 */
	public void updateAllotPermission(WebUserRoleUpdateDTO updateDTO,String userId) {
		//批量插入新权限
		if(updateDTO.getAddList().size() > 0) {
			List<UserRole> addRoleList = new ArrayList<>();
			for(String roleId : updateDTO.getAddList()) {
				UserRole userRole = new UserRole();
				userRole.setId(RandomUtil.get32UUID());
				userRole.setRoleId(roleId);
				userRole.setUserId(updateDTO.getUserId());
				userRole.setCreateMan(userId);
				userRole.setCreateAt(new Date());
				addRoleList.add(userRole);
			}
			userRoleDAO.insertBatch(addRoleList);
		}
		//批量删除权限
		if(updateDTO.getDelList().size() > 0) {
			userRoleDAO.deleteWhere("id IN " + FastSqlUtils.getInClause(updateDTO.getDelList()));
		}
	}

	public void deleteByRoleId(String roleId){
		userRoleDAO.deleteWhere("role_id = ?",roleId);
	}

}
