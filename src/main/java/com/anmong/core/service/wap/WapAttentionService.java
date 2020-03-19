package com.anmong.core.service.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.message.CommonException;
import com.anmong.core.dao.AttentionDAO;
import com.anmong.core.dao.UserDAO;
import com.anmong.core.dto.wap.attention.WapAttentionCreateDTO;
import com.anmong.core.dto.wap.attention.WapAttentionIndexDTO;
import com.anmong.core.entity.Attention;
import com.anmong.core.vo.wap.attention.WapAttentionIndexVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author songwenlong
 * 2018/3/13
 */
@Service
public class WapAttentionService {

    @Autowired
    private AttentionDAO attentionDAO;
    @Autowired
    private UserDAO userDAO;

    public void add(WapAttentionCreateDTO dto){
        Attention existAttention = attentionDAO.selectOneWhere("user_id = ? AND create_man = ?",dto.getUserId(),dto.getCreateMan());
        if (existAttention != null){
            throw CommonException.businessException("您已关注了该用户!");
        }
        Attention attention = new Attention();
        BeanUtils.copyProperties(dto,attention);
        attention.setCreateAt(new Date());
        attentionDAO.insert(attention);
        //用户粉丝数累加
        userDAO.updateSetWhereById("fans = fans + 1",dto.getUserId(),null);
    }

    public void delete(WapAttentionCreateDTO dto){
        attentionDAO.deleteWhere("user_id = ? AND create_man = ?",dto.getUserId(),dto.getCreateMan());
        //用户粉丝数减少
        userDAO.updateSetWhereById("fans = fans - 1",dto.getUserId(),null);
    }

    public ResultPage<WapAttentionIndexVO> findAllAttentionList(WapAttentionIndexDTO dto){
        return attentionDAO.findAllAttentionList(dto);

    }

    public ResultPage<WapAttentionIndexVO> findAllFansList(WapAttentionIndexDTO dto){
        return attentionDAO.findAllFansList(dto);

    }
}
