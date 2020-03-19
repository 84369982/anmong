package com.anmong.core.dao;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dto.wap.user.WapUserSearchDTO;
import com.anmong.core.dto.web.user.WebUserIndexDTO;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.vo.wap.user.WapBlogUserShowVO;
import com.anmong.core.vo.wap.user.WapUserSearchVO;
import com.anmong.core.vo.web.user.AdminUserInfo;
import org.springframework.stereotype.Repository;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.core.entity.User;

import java.util.List;

@Repository
public class UserDAO extends BaseDAO<User, String>{

    public AdminUserInfo findAdminUserInfoByUsername(String username){
        return getSQL()
                .useSql(" SELECT users.id,users.username,users.password,users.phone,users.sex,users.nickname,files.url AS head_url,users.city,users.last_login,users.login_times,users.type,users.birthday,users.grade,users.summary,users.state,users.create_man,users.create_at,files.url")
                .FROM("users")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE("users.username").eqByType(username)
                .LIMIT(1)
                .queryOne(AdminUserInfo.class);
    }

    public ResultPage<User> findAllUser(WebUserIndexDTO dto) {
        return getSQL()
                .useSql("SELECT users.id,users.username,users.nickname,files.url AS head_url,users.type,"
                        +"users.summary,users.state,u.username AS create_man,users.create_at,users.phone ")
                .FROM("users")
                .LEFT_JOIN_ON("users u","users.create_man = u.id")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE()
                .ifPresentAND("users.create_at >= ",dto.getStartTime())
                .ifPresentAND("users.create_at >= ",dto.getEndTime())
                .ifPresentAND("users.state = ",dto.getState())
                .ifPresentAND("users.username LIKE ","%"+dto.getUsername()+"%")
                .ifPresentAND("users.type = ",dto.getType())
                .ifPresentAND("users.nickname LIKE ","%"+dto.getNickname()+"%")
                .ORDER_BY("users.create_at").DESC()
                .queryPage(dto.getPageNO(),dto.getPageSize(),User.class);
    }

    public User findOneUserInfo(String id){
        return getSQL()
                .useSql("SELECT users.id,users.username,users.password,users.phone,users.sex,users.nickname,files.url AS head_url,"
                        +"users.city,users.last_login,users.login_times,users.type,users.birthday,users.grade,users.summary,users.state,"
                        +"users.create_man,users.create_at,files.url"
                )
                .FROM("users")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE("users.id").eqByType(id)
                .LIMIT(1)
                .queryOne(User.class);

    }

    public WapBlogUserShowVO findBlogUserInfo(String id){
        return getSQL()
                .useSql("SELECT users.id,users.sex,users.nickname,files.url AS head_url,")
                .append("users.city,users.grade,users.summary,users.fans")
                .FROM("users")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE("users.id").eqByType(id)
                .LIMIT(1)
                .queryOne(WapBlogUserShowVO.class);
    }

    public List<WapUserSearchVO> userSearch(WapUserSearchDTO dto){
        return getSQL()
                .SELECT("users.id","users.sex","users.nickname","files.url AS head_url","users.summary")
                .FROM("users")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE("users.nickname").LIKE("'%"+dto.getNickname()+"%'")
                .queryList(WapUserSearchVO.class);
    }

}
