package com.anmong.core.service.wap;

import com.anmong.common.message.CommonException;
import com.anmong.common.websocket.ChatSocket;
import com.anmong.common.websocket.MessageBody;
import com.anmong.core.dao.FriendApplyDAO;
import com.anmong.core.dao.MessageDAO;
import com.anmong.core.dao.UserDAO;
import com.anmong.core.dto.wap.message.WapMessageCreateDTO;
import com.anmong.core.dto.wap.message.WapUnreadMessageDTO;
import com.anmong.core.entity.FriendApply;
import com.anmong.core.entity.Message;
import com.anmong.core.entity.User;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.enums.FriendApplyEnum;
import com.anmong.core.enums.MessageEnum;
import com.anmong.core.vo.wap.message.WapUnreadMessageIndexVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author songwenlong
 * 2018/3/19
 */
@Service
public class WapMessageService {

    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private FriendApplyDAO friendApplyDAO;
    @Autowired
    private UserDAO userDAO;

    public void add(WapMessageCreateDTO dto){
        if (MessageEnum.BizType.聊天业务.code.shortValue() == dto.getBizType()){
            FriendApply meAddFriend= friendApplyDAO.selectOneWhere("friend_id = ? AND create_man = ? AND state = ?"
                    ,dto.getTargetUserId(),dto.getCreateMan(), FriendApplyEnum.state.通过.code);
            FriendApply addMeFriend= friendApplyDAO.selectOneWhere("friend_id = ? AND create_man = ? AND state = ?"
                    ,dto.getCreateMan() ,dto.getTargetUserId(), FriendApplyEnum.state.通过.code);
            if (meAddFriend == null && addMeFriend == null){
                throw CommonException.businessException("消息发送失败,请先添加好友!");
            }
        }
        User user = userDAO.findOneUserInfo(dto.getCreateMan());
        if (user == null){
            throw CommonException.businessException("用户不存在!");
        }

        Message message = new Message();
        BeanUtils.copyProperties(dto,message);
        message.setCreateAt(new Date());
        message.setCreateManNickname(user.getNickname());
        message.setCreateManHeadUrl(user.getHeadUrl());
        message.setCreateManNickname(user.getNickname());
        //发送聊天消息
        boolean result = ChatSocket.send(dto.getTargetUserId(),message);
        message.setIsRead(result ? CommonEnum.Is.是.code.shortValue() : CommonEnum.Is.否.code.shortValue());
        messageDAO.insert(message);

    }

    public List<WapUnreadMessageIndexVO> findUnreadMessage(WapUnreadMessageDTO dto){
        List<WapUnreadMessageIndexVO> voList = messageDAO.findUnreadMessage(dto);
        if(!voList.isEmpty()){
            //设置消息已读
            messageDAO.updateSetWhere("is_read = "+CommonEnum.Is.是.code,"target_user_id = ? AND create_man = ? AND is_read = "+CommonEnum.Is.否.code
                    ,dto.getUserId(),dto.getFriendId());
        }


        return voList;
    }


}
