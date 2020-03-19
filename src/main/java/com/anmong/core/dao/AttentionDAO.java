package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dto.wap.attention.WapAttentionIndexDTO;
import com.anmong.core.entity.Attention;
import com.anmong.core.vo.wap.attention.WapAttentionIndexVO;
import org.springframework.stereotype.Repository;


@Repository
public class AttentionDAO extends BaseDAO<Attention, String>{

    public ResultPage<WapAttentionIndexVO> findAllAttentionList(WapAttentionIndexDTO dto){
        return getSQL()
                .SELECT("users.nickname","users.summary","users.id user_id","files.url head_url")
                .FROM("attentions")
                .LEFT_JOIN_ON("users","users.id = attentions.user_id")
                .LEFT_JOIN_ON("files","users.head_url = files.id")
                .WHERE("attentions.create_man").eqByType(dto.getUserId())
                .ORDER_BY("attentions.create_at").DESC()
                .queryPage(dto.getPageNO(),dto.getPageSize(),WapAttentionIndexVO.class);
    }

    public ResultPage<WapAttentionIndexVO> findAllFansList(WapAttentionIndexDTO dto){
        return getSQL()
                .SELECT("users.nickname","users.summary","users.id user_id","files.url head_url")
                .FROM("attentions")
                .LEFT_JOIN_ON("users","users.id = attentions.create_man")
                .LEFT_JOIN_ON("files","users.head_url = files.id")
                .WHERE("attentions.user_id").eqByType(dto.getUserId())
                .ORDER_BY("attentions.create_at").DESC()
                .queryPage(dto.getPageNO(),dto.getPageSize(),WapAttentionIndexVO.class);
    }

}
