package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.core.entity.FriendApply;
import com.anmong.core.enums.FriendApplyEnum;
import com.anmong.core.vo.wap.friendapply.WapFriendApplyIndexVO;
import com.anmong.core.vo.wap.friendapply.WapFriendIndexVO;
import com.anmong.core.vo.wap.user.WapUserSearchVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author songwenlong
 * 2018/3/16
 */
@Repository
public class FriendApplyDAO extends BaseDAO<FriendApply, String> {

    public List<WapFriendApplyIndexVO> findFriendApplyList(String userId){
        return getSQL()
                .SELECT("users.id","users.sex","users.nickname","files.url AS head_url","friend_apply.content")
                .FROM("friend_apply")
                .LEFT_JOIN_ON("users","friend_apply.friend_id = users.id")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE("friend_apply.friend_id").eqByType(userId)
                .queryList(WapFriendApplyIndexVO.class);
    }

    public List<WapFriendIndexVO> findMeAddfriend(String userId){
        return getSQL()
                .SELECT("users.id","users.sex","users.nickname","files.url AS head_url","users.summary")
                .FROM("friend_apply")
                .LEFT_JOIN_ON("users","friend_apply.friend_id = users.id")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE("friend_apply.create_man").eqByType(userId)
                .AND("friend_apply.state").eqByType(FriendApplyEnum.state.通过.code)
                .queryList(WapFriendIndexVO.class);
    }

    public List<WapFriendIndexVO> findAddMefriend(String userId){
        return getSQL()
                .SELECT("users.id","users.sex","users.nickname","files.url AS head_url","users.summary")
                .FROM("friend_apply")
                .LEFT_JOIN_ON("users","friend_apply.create_man = users.id")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE("friend_apply.friend_id").eqByType(userId)
                .AND("friend_apply.state").eqByType(FriendApplyEnum.state.通过.code)
                .queryList(WapFriendIndexVO.class);
    }


}
