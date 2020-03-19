package com.anmong.core.dao;

import com.anmong.common.config.MyConfig;
import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dto.web.role.WebRoleIndexDTO;
import com.anmong.core.entity.Permission;
import com.anmong.core.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAO extends BaseDAO<Role, String>{

    public List<String> findAllRoleCodeByUserId(String userId){
        return getSQL()
                .SELECT("roles.code")
                .FROM("users ,user_role ,roles")
                .WHERE("users.id").eqByType(userId)
                .append("AND users.id = user_role.user_id AND user_role.role_id = roles.id")
                .queryStringList();
    }

    public ResultPage<Role> findAllRole(WebRoleIndexDTO dto) {
        return getSQL()
                .useSql("SELECT users.username AS create_man,roles.id,roles.name,roles.code,roles.note,"
                        +"roles.state,roles.create_man AS createMan,roles.create_at ")
                .FROM("roles")
                .LEFT_JOIN_ON("users","roles.create_man=users.id")
                .WHERE()
                .ifPresentAND("users.create_at >= ",dto.getStartTime())
                .ifPresentAND("users.create_at >= ",dto.getEndTime())
                .ifPresentAND("users.state = ",dto.getState())
                .ifTrueAND("roles.code ! = ", MyConfig.getConfig("system.role.rootCode"),!Boolean.parseBoolean(MyConfig.getConfig("system.run.isDebug")))
                .ORDER_BY("roles.create_at").DESC()
                .queryPage(dto.getPageNO(),dto.getPageSize(),Role.class);
    }

}
