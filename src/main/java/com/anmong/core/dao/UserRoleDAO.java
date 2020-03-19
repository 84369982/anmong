package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.util.DateUtil;
import com.anmong.core.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserRoleDAO extends BaseDAO<UserRole, String>{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertBatch(List<UserRole> entityList){
        StringBuilder sql = new StringBuilder("INSERT INTO user_role (id,user_id,role_id,create_man,create_at) values ");
        for (UserRole entity : entityList){
            sql.append("('"+entity.getId()+"','"+entity.getUserId()+"','"+entity.getRoleId()+"','"+entity.getCreateMan())
                    .append("','" + DateUtil.getYYYYmmDDhhMMss(entity.getCreateAt())+"'),");
        }
        sql.replace(sql.lastIndexOf(","),sql.lastIndexOf(",")+1,";");
        namedParameterJdbcTemplate.update(sql.toString(), EmptySqlParameterSource.INSTANCE);
    }


}
