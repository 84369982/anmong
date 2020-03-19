package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dto.wap.comment.WapCommentIndexDTO;
import com.anmong.core.entity.Comment;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.vo.wap.comment.WapCommentIndexVO;
import com.anmong.core.vo.wap.comment.WapHotCommentVO;
import com.anmong.core.vo.wap.comment.WapParentCommentIndexVO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CommentDAO extends BaseDAO<Comment, String>{

    public List<WapHotCommentVO> findHotComment(String bizId){
        return getSQL()
                .SELECT("comments.content","comments.content_type","users.nickname create_man","files.url")
                .FROM("comments")
                .LEFT_JOIN_ON("users","users.id = comments.create_man")
                .LEFT_JOIN_ON("files","comments.url = files.id")
                .WHERE("comments.biz_id").eqByType(bizId)
                .AND("comments.state").eqByType(CommonEnum.State.启用.code)
                .ORDER_BY("comments.like_quantity").ASC()
                .LIMIT(3)
                .queryList(WapHotCommentVO.class);
    }

    public ResultPage<WapCommentIndexVO> findArticleCommentList(WapCommentIndexDTO dto){
        return getSQL()
                .SELECT("comments.content","comments.content_type","users.nickname create_man","files.url head_url","attach.url")
                .append_SELECT("comments.state","comments.like_quantity","comments.id","comments.create_at","comments.parent_id")
                .FROM("comments")
                .LEFT_JOIN_ON("users","users.id = comments.create_man")
                .LEFT_JOIN_ON("files","users.head_url = files.id")
                .LEFT_JOIN_ON("files attach","comments.url = attach.id")
                .WHERE("comments.biz_id").eqByType(dto.getArticleId())
                .ORDER_BY("comments.like_quantity").ASC()
                .queryPage(dto.getPageNO(),dto.getPageSize(),WapCommentIndexVO.class);
    }

    public WapParentCommentIndexVO findParentComment(String parentCommentId){
        return getSQL()
                .SELECT("comments.content","comments.content_type","users.nickname create_man","attach.url")
                .append_SELECT("comments.state","comments.like_quantity","comments.create_at","comments.parent_id")
                .FROM("comments")
                .LEFT_JOIN_ON("users","users.id = comments.create_man")
                .LEFT_JOIN_ON("files attach","comments.url = attach.id")
                .WHERE("comments.id").eqByType(parentCommentId)
                .queryOne(WapParentCommentIndexVO.class);

    }





}
