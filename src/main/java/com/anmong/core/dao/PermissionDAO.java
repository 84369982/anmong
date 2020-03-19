package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dto.web.permission.WebPermissionIndexDTO;
import com.anmong.core.entity.Permission;
import com.anmong.core.enums.PermissionEnum;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class PermissionDAO extends BaseDAO<Permission, String>{

    public List<String> findAllpermissionUrlByUserId(String userId){
        return getSQL()
                .SELECT("permissions.url")
                .FROM("users ,user_role ,roles ,role_permission ,permissions")
                .WHERE("users.id").eqByType(userId)
                .append("AND users.id = user_role.user_id AND user_role.role_id = roles.id AND roles.id = role_permission.role_id AND role_permission.permission_id = permissions.id")
                .queryStringList();
    }

    public List<Permission> findAllMenu(String userId){
        return getSQL()
                .useSql("SELECT p.id,p.url,p.type,p.icon,p.name,p.parent_id,p.sort,p.code,p.state")
                .FROM("permissions AS p ,role_permission AS rp ,roles AS r ,user_role AS ur ,users AS u")
                .WHERE("u.id").eqByType(userId)
                .AND("p.state").eqByType(PermissionEnum.State.启用.code)
                .AND("p.type").eqByType(PermissionEnum.Type.菜单.code)
                .append(" AND u.id=ur.user_id AND ur.role_id=r.id AND r.id = rp.role_id AND rp.permission_id= p.id ")
                .queryList(Permission.class);
    }

    public ResultPage<Permission> findAllPermission(WebPermissionIndexDTO dto){
        return getSQL()
                .useSql("SELECT permissions.url,permissions.id,permissions.type,permissions.icon,permissions.note,"
                        +"permissions.sort,permissions.`name`,permissions.`code`,permissions.state,permissions.is_root AS isRoot,"
                        +"permissions.create_at AS createAt,permissions.parent_id AS parentId,users.username AS createMan"
                )
                .FROM("permissions")
                .LEFT_JOIN_ON("users","users.id = permissions.create_man")
                .WHERE()
                .ifPresentAND("permissions.type = " ,dto.getType())
                .ifPresentAND("permissions.create_at >= " ,dto.getStartTime())
                .ifPresentAND("permissions.create_at <= " ,dto.getEndTime())
                .ifPresentAND("permissions.name LIKE " ,"%"+dto.getName()+"%")
                .queryPage(dto.getPageNO(),dto.getPageSize(),Permission.class);
    }

    /**
     * 除自身，是否有相同的排序
     * @param sort
     * @return
     */
    public Permission findOneTopMenuBySortExceptSelf(Integer sort,String id){
        return getSQL()
                .SELECT_all_FROM("permissions")
                .WHERE("type").eqByType(PermissionEnum.Type.菜单.code)
                .AND("state").eqByType(PermissionEnum.State.启用.code)
                .AND("sort").eqByType(sort)
                .AND("parent_id IS NULL")
                .ifTrueAND("id != ",id,!StringUtils.isEmpty(id))
                .LIMIT(1)
                .queryOne(Permission.class);
    }

    /**
     * 除自身，是否有相同的排序
     * @param sort
     * @return
     */
    public Permission findOneSubMenuBySortExceptSelf(Integer sort,String parentId,String id){
        return getSQL()
                .SELECT_all_FROM("permissions")
                .WHERE("type").eqByType(PermissionEnum.Type.菜单.code)
                .AND("state").eqByType(PermissionEnum.State.启用.code)
                .AND("sort").eqByType(sort)
                .AND("parent_id").eqByType(parentId)
                .ifTrueAND("id != ",id,!StringUtils.isEmpty(id))
                .LIMIT(1)
                .queryOne(Permission.class);
    }



}
