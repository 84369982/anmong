package com.anmong.core.service.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dao.PermissionDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.anmong.common.message.CommonException;
import com.anmong.common.pagination.DataTable;

import com.anmong.core.dto.web.permission.WebFunctionPermissionCreateDTO;
import com.anmong.core.dto.web.permission.WebFunctionPermissionUpdateDTO;
import com.anmong.core.dto.web.permission.WebPermissionIndexDTO;
import com.anmong.core.entity.Permission;
import com.anmong.core.enums.PermissionEnum;
import com.anmong.core.vo.web.permission.SubMenuShowVO;
import com.anmong.core.vo.web.permission.WebPermissionShowVO;
import com.anmong.core.vo.web.permission.WebParentMenuIndexVO;


@Service
public class WebPermissionService {

	@Autowired
	private WebRolePermissionService webRolePermissionService;

	@Autowired
	private PermissionDAO permissionDAO;

	public List<WebParentMenuIndexVO> findAllMenu(String userId){
		List<Permission> permissionList = permissionDAO.findAllMenu(userId);
		List<WebParentMenuIndexVO> webParentMenuIndexVOList = new ArrayList<>();
		if(!permissionList.isEmpty()) {
			for(Permission permission :permissionList) {
				//parentId为空表示当前数据为父菜单
				if(StringUtils.isEmpty(permission.getParentId())) {
					WebParentMenuIndexVO webParentMenuIndexVO = new WebParentMenuIndexVO();
					webParentMenuIndexVO.setIcon(permission.getIcon());
					webParentMenuIndexVO.setName(permission.getName());
					webParentMenuIndexVO.setSort(permission.getSort());
					List<SubMenuShowVO> subMenuShowVOList = new ArrayList<>();
					for(Permission p :permissionList) {
						//当前元素的父id不为空且等于上级元素时，则此为上级元素的子元素
						if(!StringUtils.isEmpty(p.getParentId()) && permission.getId().equals(p.getParentId())) {
							SubMenuShowVO subMenuShowVO = new SubMenuShowVO();
							subMenuShowVO.setSort(p.getSort());
							subMenuShowVO.setUrl(p.getUrl());
							subMenuShowVO.setName(p.getName());
							subMenuShowVOList.add(subMenuShowVO);
						}
					}
					//子菜单排序
					if(subMenuShowVOList.size() > 1) {
						Collections.sort(subMenuShowVOList, new Comparator<SubMenuShowVO>() {
							@Override
							public int compare(SubMenuShowVO o1, SubMenuShowVO o2) {
								Integer distance1 = o1.getSort();
								Integer distance2 = o2.getSort();
								return distance1.intValue() - distance2.intValue();
							}
						});
					}
					webParentMenuIndexVO.setSubMenuList(subMenuShowVOList);
					webParentMenuIndexVOList.add(webParentMenuIndexVO);
				}
			}
			//父菜单排序
			if(webParentMenuIndexVOList.size() > 1) {
				Collections.sort(webParentMenuIndexVOList, new Comparator<WebParentMenuIndexVO>() {
					@Override
					public int compare(WebParentMenuIndexVO o1, WebParentMenuIndexVO o2) {
						Integer sort1 = o1.getSort();
						Integer sort2 = o2.getSort();
						return sort1.intValue() - sort2.intValue();
					}
				});
			}

		}
		return webParentMenuIndexVOList;
	}
	
	public  DataTable findAll(HttpServletRequest request,WebPermissionIndexDTO dto){
		DataTable dt = DataTable.getInstance(request, null);
		dto.setPageNO(dt.getStart()/dt.getLength() + 1);
		dto.setPageSize(dt.getLength());
		ResultPage<Permission> page = permissionDAO.findAllPermission(dto);
		dt.setData(page.getContent());
		dt.setRecordsTotal(page.getTotalElements());
		dt.setRecordsFiltered(page.getTotalElements());
		return dt;
	}
	
	/**
	 * 查询所有二级菜单
	 * @return
	 */
	public List<Permission> findAllSubmenu(){
		return permissionDAO.selectWhere("type = ? AND parent_id IS NOT NULL",PermissionEnum.Type.菜单.code);
	}
	
	/**
	 * 查询所有一级菜单
	 * @return
	 */
	public List<Permission> findAllTopmenu(){
		return permissionDAO.selectWhere("type = ? AND parent_id IS NULL",PermissionEnum.Type.菜单.code);
	}
	
	public void addPermission(Permission permission) {
		permission.setCreateAt(new Date());
		if(PermissionEnum.Type.菜单.code.intValue() == permission.getType().intValue()) {
			permission.setMenuId(null);
			if(StringUtils.isEmpty(permission.getParentId())) {
				Permission savedMenu = permissionDAO.findOneTopMenuBySortExceptSelf(permission.getSort(),null);
				if(savedMenu != null) {
					throw CommonException.businessException("该类型下菜单排序已存在，请重新设置!");
				}
				//顶级菜单此项为空
				permission.setParentId(null);
			}
			else {
				Permission savedMenu = permissionDAO.findOneSubMenuBySortExceptSelf(permission.getSort(),permission.getParentId(),null);
				if(savedMenu != null) {
					throw CommonException.businessException("该类型下菜单排序已存在，请重新设置!");
				}
				permission.setIcon(null);
			}
			permissionDAO.insert(permission);
		}
		else {
			WebFunctionPermissionCreateDTO functionCreateDTO = new WebFunctionPermissionCreateDTO();
			BeanUtils.copyProperties(permission, functionCreateDTO);
			Permission functionPermission  = new Permission();
			BeanUtils.copyProperties(functionCreateDTO, functionPermission);
			permissionDAO.insert(functionPermission);
		}
	}

	
	public void delete(String id) {
		permissionDAO.deleteOneById(id);
		webRolePermissionService.deleteByPermissionId(id);
	}
	
	public WebPermissionShowVO findOne(String id) {
		Permission permission = permissionDAO.selectOneById(id);
		WebPermissionShowVO showVO = new WebPermissionShowVO();
		BeanUtils.copyProperties(permission, showVO);
		//查询上级菜单名称
		if(!StringUtils.isEmpty(permission.getParentId())) {
			Permission parentMenu = permissionDAO.selectOneById(permission.getParentId());
            if(parentMenu != null) {
            	showVO.setParentName(parentMenu.getName());
            }
		}
		//查询所属菜单名称
		if(!StringUtils.isEmpty(permission.getMenuId())) {
			Permission belongMenu = permissionDAO.selectOneById(permission.getMenuId());
            if(belongMenu != null) {
            	showVO.setMenuName(belongMenu.getName());
            }
		}
		return showVO;
	}
	
	public void updatePermission(Permission permission) {
		permission.setCreateAt(new Date());
		if(PermissionEnum.Type.菜单.code.intValue() == permission.getType().intValue()) {
			permission.setMenuId(null);
			if(StringUtils.isEmpty(permission.getParentId())) {
				Permission savedMenu = permissionDAO.findOneTopMenuBySortExceptSelf(permission.getSort(),permission.getId());
				if(savedMenu != null) {
					throw CommonException.businessException("该类型下菜单排序已存在，请重新设置!");
				}
				//顶级菜单此项为空
				permission.setParentId(null);
			}
			else {
				Permission savedMenu = permissionDAO.findOneSubMenuBySortExceptSelf(permission.getSort(),permission.getParentId(),permission.getId());
				if(savedMenu != null) {
					throw CommonException.businessException("该类型下菜单排序已存在，请重新设置!");
				}
				permission.setIcon(null);
			}
			permissionDAO.updateSelective(permission);
		}
		else {
			WebFunctionPermissionUpdateDTO functionUpdateDTO = new WebFunctionPermissionUpdateDTO();
			BeanUtils.copyProperties(permission, functionUpdateDTO);
			Permission functionPermission  = new Permission();
			BeanUtils.copyProperties(functionUpdateDTO, functionPermission);
			functionPermission.setSort(null);
			permissionDAO.update(permission);
		}
	}
	

}
