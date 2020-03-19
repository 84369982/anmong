package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dto.wap.article.WapBlogIndexDTO;
import com.anmong.core.dto.wap.article.WapMyBlogIndexDTO;
import com.anmong.core.entity.Article;
import com.anmong.core.enums.ArticleEnum;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.vo.wap.article.WapBlogIndexVO;
import com.anmong.core.vo.wap.article.WapBlogShowVO;
import com.anmong.core.vo.wap.article.WapForwardBlogVO;
import org.springframework.stereotype.Repository;


@Repository
public class ArticleDAO extends BaseDAO<Article, String>{

    public ResultPage<WapBlogIndexVO> findAllBlog(WapBlogIndexDTO dto){
        return getSQL()
                .useSql("SELECT articles.id,articles.article_type,articles.content_type,articles.read_quantity,articles.forward_quantity,")
                .append("articles.comment_quantity,articles.is_hot,articles.txt,articles.is_forward,articles.source_id,articles.like_quantity,")
                .append("articles.create_at,files.url head_url,users.nickname create_man,articles.title,articles.create_man user_id")
                .FROM("articles")
                .LEFT_JOIN_ON("users","articles.create_man = users.id")
                .LEFT_JOIN_ON("files","users.head_url = files.id")
                .WHERE("articles.state").eqByType(CommonEnum.State.启用.code)
                .ifTrueAND("articles.is_hot = ",CommonEnum.Is.是.code, ArticleEnum.WapIndexType.热门.code.equals(dto.getType()))
                .ORDER_BY("articles.create_at").DESC()
                .queryPage(dto.getPageNO(),dto.getPageSize(),WapBlogIndexVO.class);
    }

    public WapForwardBlogVO findForwardBlog(String id){
        return getSQL()
                .useSql("SELECT articles.id,articles.article_type,articles.content_type,articles.txt,")
                .append("files.url head_url,users.nickname create_man,articles.title,attach.url title_img")
                .FROM("articles")
                .LEFT_JOIN_ON("users","articles.create_man = users.id")
                .LEFT_JOIN_ON("files","users.head_url = files.id")
                .LEFT_JOIN_ON("files attach","articles.title_img = attach.id")
                .WHERE("articles.state").eqByType(CommonEnum.State.启用.code)
                .AND("articles.id").eqByType(id)
                .queryOne(WapForwardBlogVO.class);
    }

    public WapBlogShowVO findOneBlog(String id){
        return getSQL()
                .useSql("SELECT articles.id,articles.article_type,articles.content_type,articles.read_quantity,articles.forward_quantity,")
                .append("articles.comment_quantity,articles.is_hot,articles.txt,articles.is_forward,articles.source_id,articles.like_quantity,")
                .append("articles.create_at,files.url head_url,users.nickname create_man,articles.create_man user_id")
                .FROM("articles")
                .LEFT_JOIN_ON("users","articles.create_man = users.id")
                .LEFT_JOIN_ON("files","users.head_url = files.id")
                .WHERE("articles.state").eqByType(CommonEnum.State.启用.code)
                .AND("articles.id").eqByType(id)
                .LIMIT(1)
                .queryOne(WapBlogShowVO.class);
    }

    public ResultPage<WapBlogIndexVO> findAllByUserId(WapMyBlogIndexDTO dto){
        return getSQL()
                .useSql("SELECT articles.id,articles.article_type,articles.content_type,articles.read_quantity,articles.forward_quantity,")
                .append("articles.comment_quantity,articles.is_hot,articles.txt,articles.is_forward,articles.source_id,articles.like_quantity,")
                .append("articles.create_at,files.url head_url,users.nickname create_man,articles.title,articles.create_man user_id")
                .FROM("articles")
                .LEFT_JOIN_ON("users","articles.create_man = users.id")
                .LEFT_JOIN_ON("files","users.head_url = files.id")
                .WHERE("articles.state").eqByType(CommonEnum.State.启用.code)
                .AND("articles.create_man").eqByType(dto.getUserId())
                .ORDER_BY("articles.create_at").DESC()
                .queryPage(dto.getPageNO(),dto.getPageSize(),WapBlogIndexVO.class);
    }



}
