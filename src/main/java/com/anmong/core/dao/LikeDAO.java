package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dto.wap.like.WapLikeIndexDTO;
import com.anmong.core.entity.Like;
import com.anmong.core.vo.wap.like.WapLikeIndexVO;
import org.springframework.stereotype.Repository;


@Repository
public class LikeDAO extends BaseDAO<Like, String>{

    public ResultPage<WapLikeIndexVO> findAllLikeInfo(WapLikeIndexDTO dto){
        return getSQL()
                .SELECT("files.url head_url","users.nickname create_man","likes.create_at","users.id user_id")
                .FROM("likes")
                .LEFT_JOIN_ON("users","likes.create_man = users.id")
                .LEFT_JOIN_ON("files","files.id = users.head_url")
                .WHERE("likes.biz_id").eqByType(dto.getArticleId())
                .queryPage(dto.getPageNO(),dto.getPageSize(),WapLikeIndexVO.class);

    }


}
