package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.core.dto.wap.message.WapUnreadMessageDTO;
import com.anmong.core.entity.Message;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.vo.wap.message.WapUnreadMessageIndexVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author songwenlong
 * 2018/3/16
 */
@Repository
public class MessageDAO extends BaseDAO<Message, String> {

    public List<WapUnreadMessageIndexVO> findUnreadMessage(WapUnreadMessageDTO dto) {
        return getSQL()
                .SELECT("messages.content","messages.create_at","files.url head_url")
                .FROM("messages")
                .LEFT_JOIN_ON("users","messages.create_man = users.id")
                .LEFT_JOIN_ON("files","users.head_url=files.id")
                .WHERE("messages.target_user_id").eqByType(dto.getUserId())
                .AND("messages.create_man").eqByType(dto.getFriendId())
                .AND("messages.is_read").eqByType(CommonEnum.Is.否.code)
                .ORDER_BY("messages.create_at").DESC()
                .queryList(WapUnreadMessageIndexVO.class);
    }

}
