package com.anmong.core.service.wap;

import com.anmong.common.config.MyConfig;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dao.ArticleDAO;
import com.anmong.core.dao.CommentDAO;
import com.anmong.core.dao.FileDAO;
import com.anmong.core.dto.wap.comment.WapCommentCreateDTO;
import com.anmong.core.dto.wap.comment.WapCommentIndexDTO;
import com.anmong.core.entity.Article;
import com.anmong.core.entity.Comment;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.service.web.WebFileService;
import com.anmong.core.vo.wap.comment.WapCommentIndexVO;
import com.anmong.core.vo.wap.comment.WapParentCommentIndexVO;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author songwenlong
 * 2018/3/8
 */
@Service
public class WapCommentService {

    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private WebFileService webFileService;
    @Autowired
    private ArticleDAO articleDAO;

    public ResultPage<WapCommentIndexVO> findArticleCommentList(WapCommentIndexDTO dto){
        ResultPage<WapCommentIndexVO> resultPage = commentDAO.findArticleCommentList(dto);
        for (WapCommentIndexVO vo : resultPage.getContent()){
            if (vo.getState().shortValue() == CommonEnum.State.禁用.code.shortValue()){
                vo.setContent("该评论已被删除!");
            }
            //查询父评论
            List<WapParentCommentIndexVO> parentCommentIndexVOList = new ArrayList<>();
            if (!StringUtils.isEmpty(vo.getParentId())){
                String parentId = vo.getParentId();
                WapParentCommentIndexVO parentCommentIndexVO;
                do {
                    parentCommentIndexVO = commentDAO.findParentComment(parentId);
                    if (parentCommentIndexVO != null ){
                        if (parentCommentIndexVO.getState().shortValue() == CommonEnum.State.禁用.code.shortValue()){
                            parentCommentIndexVO.setContent("该评论已被删除!");
                        }
                        parentCommentIndexVOList.add(parentCommentIndexVO);
                        parentId = parentCommentIndexVO.getParentId();
                    }

                }
               while (!StringUtils.isEmpty(parentCommentIndexVO.getParentId()));

            }
            //反转集合,实现正序排
            Collections.reverse(parentCommentIndexVOList);
            vo.setParentCommentList(parentCommentIndexVOList);
        }
        return resultPage;
    }

    public void add(WapCommentCreateDTO dto){
        Comment comment = new Comment();
        BeanUtils.copyProperties(dto,comment);
        comment.setCreateAt(new Date());
        comment.setLikeQuantity(0);
        comment.setState(CommonEnum.State.启用.code.shortValue());
        commentDAO.insert(comment);
        //处理关联文件
        if (!StringUtils.isEmpty(dto.getUrl())){
            webFileService.updateBizIdByFileId(comment.getId(),dto.getUrl());
        }
        Article article = articleDAO.selectNotNullOneById(dto.getBizId(),"微博不存在");
        int hotThreshold = Integer.parseInt(MyConfig.getConfig("blog.state.hot.quantity"));
        //超过阀值则变为热门微博
        if ((article.getCommentQuantity().intValue()+1) >= hotThreshold){
            articleDAO.updateSetWhere("comment_quantity = comment_quantity + 1,is_hot = ?","id = ?"
                    , CommonEnum.Is.是.code,dto.getBizId());
        }
        else {
            //文章评论数累加
            articleDAO.updateSetWhereById("comment_quantity = comment_quantity + 1",dto.getBizId(),null);
        }



    }
}
