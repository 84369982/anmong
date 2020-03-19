package com.anmong.core.service.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.message.CommonException;
import com.anmong.core.dao.ArticleDAO;
import com.anmong.core.dao.ArticleForwardDAO;
import com.anmong.core.dto.wap.articleforward.WapForwardBlogIndexDTO;
import com.anmong.core.dto.wap.articleforward.WapForwardCreateDTO;
import com.anmong.core.entity.Article;
import com.anmong.core.entity.ArticleForward;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.vo.wap.article.WapForwardIndexVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author songwenlong
 * 2018/3/7
 */
@Service
public class WapArticleForwardService {

    @Autowired
    private ArticleForwardDAO articleForwardDAO;
    @Autowired
    private ArticleDAO articleDAO;

    public ResultPage<WapForwardIndexVO> findAllForwardInfo(WapForwardBlogIndexDTO dto){
        return articleForwardDAO.findAllForwardInfo(dto);
    }

    public void add(WapForwardCreateDTO dto){
        Article targetArticle = articleDAO.selectNotNullOneById(dto.getSourceId(),"文章不存在!");
        if (CommonEnum.State.禁用.code.shortValue() == targetArticle.getState().shortValue()){
            throw CommonException.businessException("文章禁止转载!");
        }
        if (CommonEnum.Is.是.code.shortValue() == targetArticle.getIsForward().shortValue()){
            throw CommonException.businessException("转载转载的文章只能转载源文章id!");
        }
        //保存新文章
        Article article = new Article();
        BeanUtils.copyProperties(targetArticle,article);
        article.setIsForward(CommonEnum.Is.是.code.shortValue());
        article.setIsHot(CommonEnum.Is.否.code.shortValue());
        article.setReadQuantity(0);
        article.setForwardQuantity(0);
        article.setLikeQuantity(0);
        article.setCommentQuantity(0);
        article.setId(null);
        article.setTitle(null);
        article.setTitleImg(null);
        article.setCreateAt(new Date());
        article.setContent(null);
        article.setTxt(dto.getTxt());
        article.setSourceId(dto.getSourceId());
        article.setCreateMan(dto.getCreateMan());
        articleDAO.insert(article);
        //保存转载信息
        ArticleForward articleForward = new ArticleForward();
        articleForward.setArticleId(article.getId());
        articleForward.setComment(dto.getTxt());
        articleForward.setCreateMan(dto.getCreateMan());
        articleForward.setCreateAt(new Date());
        articleForwardDAO.insert(articleForward);
        //原始文章转发数累计
        articleDAO.updateSetWhereById("forward_quantity = forward_quantity + 1",dto.getSourceId(),null);

    }
}
