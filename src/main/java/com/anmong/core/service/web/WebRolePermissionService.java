package com.anmong.core.service.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.anmong.common.fastsql.util.FastSqlUtils;
import com.anmong.core.dao.PermissionDAO;
import com.anmong.core.dao.RolePermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anmong.common.config.MyConfig;
import com.anmong.common.util.RandomUtil;
import com.anmong.core.dto.web.rolepermission.WebRolePermissionUpdateDTO;
import com.anmong.core.entity.Permission;
import com.anmong.core.entity.RolePermission;
import com.anmong.core.enums.PermissionEnum;

@Service
public class WebRolePermissionService {



	@Autowired
	private PermissionDAO permissionDAO;

	@Autowired
	private RolePermissionDAO rolePermissionDAO;
	
	/**
	 * 查询所有可分配的权限
	 */
	public List<Permission> findAllAllotPermission() {
		return permissionDAO.getSQL()
				.SELECT_all_FROM("permissions")
				.WHERE("state").eqByType(PermissionEnum.State.启用.code)
				.ifTrueAND("is_root = ",PermissionEnum.IsRoot.否.code,!Boolean.parseBoolean(MyConfig.getConfig("system.run.isDebug")))
				.queryList(Permission.class);
	}
	
	public List<RolePermission> findOwnedPermission(String roleId){
		return rolePermissionDAO.selectWhere("role_id = ?",roleId);
	}
	
	/**
	 * 分配权限
	 */
	public void updateAllotPermission(WebRolePermissionUpdateDTO updateDTO,String userId) {
		//批量插入新权限
		if(updateDTO.getAddList().size() > 0) {
			List<RolePermission> addPermissionList = new ArrayList<>();
			for(String addPermissionId : updateDTO.getAddList()) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setId(RandomUtil.get32UUID());
				rolePermission.setPermissionId(addPermissionId);
				rolePermission.setRoleId(updateDTO.getRoleId());
				rolePermission.setCreateMan(userId);
				rolePermission.setCreateAt(new Date());
				addPermissionList.add(rolePermission);
			}
			rolePermissionDAO.insertBatch(addPermissionList);
		}
		//批量删除权限
		if(updateDTO.getDelList().size() > 0) {
			rolePermissionDAO.deleteWhere("id IN " + FastSqlUtils.getInClause(updateDTO.getDelList()));
		}
	}
	
	public void deleteByPermissionId(String permissionId) {
		rolePermissionDAO.deleteWhere("permission_id = ? " , permissionId);
		
	}

	public void deleteByRoleId(String roleId){
		rolePermissionDAO.deleteWhere("role_id = ? " , roleId);
	}

}
