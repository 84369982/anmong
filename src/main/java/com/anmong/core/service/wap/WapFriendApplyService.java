package com.anmong.core.service.wap;

import com.anmong.common.message.CommonException;
import com.anmong.core.dao.FriendApplyDAO;
import com.anmong.core.dto.wap.friendapply.WapFriendApplyCreateDTO;
import com.anmong.core.dto.wap.friendapply.WapFriendApplyHandleDTO;
import com.anmong.core.entity.FriendApply;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.enums.FriendApplyEnum;
import com.anmong.core.vo.wap.friendapply.WapFriendApplyIndexVO;
import com.anmong.core.vo.wap.friendapply.WapFriendIndexVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author songwenlong
 * 2018/3/16
 */
@Service
public class WapFriendApplyService {

    @Autowired
    private FriendApplyDAO friendApplyDAO;

    public void add(WapFriendApplyCreateDTO dto){
        int countThey = friendApplyDAO.countWhere(" friend_id = ? AND create_man = ?",dto.getFriendId(),dto.getCreateMan());
        if (countThey > 0){
            throw CommonException.businessException("您已申请过好友，请耐心等待对方验证！");
        }
        int countMine = friendApplyDAO.countWhere(" friend_id = ? AND create_man = ?",dto.getCreateMan(),dto.getFriendId());
        if (countMine > 0){
            throw CommonException.businessException("对方向已您申请了好友，请先处理！");
        }
        FriendApply friendApply = new FriendApply();
        BeanUtils.copyProperties(dto,friendApply);
        friendApply.setCreateAt(new Date());
        friendApply.setState(FriendApplyEnum.state.待验证.code.shortValue());
        friendApplyDAO.insert(friendApply);
    }
    public List<WapFriendApplyIndexVO> findFriendApplyList(String userId){
        return friendApplyDAO.findFriendApplyList(userId);
    }

    public void handle(WapFriendApplyHandleDTO dto){
        FriendApply friendApply = friendApplyDAO.selectOneWhere("id = ? AND state = ?",dto.getId(),FriendApplyEnum.state.待验证.code);
        if (friendApply == null){
            throw CommonException.businessException("好友申请信息不存在!");
        }
        if (!friendApply.getFriendId().equals(dto.getUserId())){
            throw CommonException.businessException("您没有权限处理!");
        }
        if (CommonEnum.Is.是.code.shortValue() == dto.getResult().shortValue()){
            friendApply.setState(FriendApplyEnum.state.通过.code.shortValue());
            friendApply.setFriendNote(dto.getNote());
            friendApplyDAO.update(friendApply);
        }
        else {
            friendApplyDAO.deleteOneById(dto.getId());
        }

    }

    public List<WapFriendIndexVO> findFriendList(String userId){
        List<WapFriendIndexVO> meAddfriend = friendApplyDAO.findMeAddfriend(userId);
        List<WapFriendIndexVO> addMefriend = friendApplyDAO.findAddMefriend(userId);
        meAddfriend.addAll(addMefriend);
        return meAddfriend;
    }
}
