package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dto.wap.articleforward.WapForwardBlogIndexDTO;
import com.anmong.core.entity.ArticleForward;
import com.anmong.core.vo.wap.article.WapForwardIndexVO;
import org.springframework.stereotype.Repository;


@Repository
public class ArticleForwardDAO extends BaseDAO<ArticleForward, String> {

    public ResultPage<WapForwardIndexVO> findAllForwardInfo(WapForwardBlogIndexDTO dto){
        return getSQL()
                .SELECT("files.url head_url","users.nickname create_man","article_forward.create_at")
                .append_SELECT("article_forward.comment","users.id user_id")
                .FROM("article_forward")
                .LEFT_JOIN_ON("users","article_forward.create_man = users.id")
                .LEFT_JOIN_ON("files","files.id = users.head_url")
                .WHERE("article_forward.article_id").eqByType(dto.getArticleId())
                .queryPage(dto.getPageNO(),dto.getPageSize(),WapForwardIndexVO.class);

    }


}
