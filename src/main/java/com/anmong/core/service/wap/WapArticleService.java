package com.anmong.core.service.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dao.ArticleDAO;
import com.anmong.core.dao.CommentDAO;
import com.anmong.core.dao.FileDAO;
import com.anmong.core.dto.wap.article.WapArticleCreateDTO;
import com.anmong.core.dto.wap.article.WapBlogIndexDTO;
import com.anmong.core.dto.wap.article.WapMyBlogIndexDTO;
import com.anmong.core.entity.Article;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.service.web.WebFileService;
import com.anmong.core.vo.wap.article.WapBlogShowVO;
import com.anmong.core.vo.wap.comment.WapHotCommentVO;
import com.anmong.core.vo.wap.file.FileIndexVO;
import com.anmong.core.vo.wap.article.WapBlogIndexVO;
import com.anmong.core.vo.wap.article.WapForwardBlogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author songwenlong
 * 2018/2/27
 */
@Service
public class WapArticleService {

    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private FileDAO fileDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private WebFileService webFileService;

    public void add(WapArticleCreateDTO dto){
        Article article = new Article();
        BeanUtils.copyProperties(dto,article);
        article.setCommentQuantity(0);
        article.setLikeQuantity(0);
        article.setForwardQuantity(0);
        article.setReadQuantity(0);
        article.setCreateAt(new Date());
        article.setState(CommonEnum.State.启用.code.shortValue());
        article.setIsHot(CommonEnum.Is.否.code.shortValue());
        article.setIsForward(CommonEnum.Is.否.code.shortValue());
        articleDAO.insert(article);
        //附件关联
        for (String fileId : dto.getFileIdList()){
            webFileService.updateBizIdByFileId(article.getId(),fileId);
        }

    }

    public ResultPage<WapBlogIndexVO> findAllBlog(WapBlogIndexDTO dto){
        ResultPage<WapBlogIndexVO> resultPage = articleDAO.findAllBlog(dto);
        handleForwardBlogInfo(resultPage);
        return resultPage;
    }

    public WapBlogShowVO findOneBlog(String id){
        WapBlogShowVO vo = articleDAO.findOneBlog(id);
        //处理转载的文章
        if (CommonEnum.Is.是.code.shortValue() == vo.getIsForward()){
           // 转载文章的阅读量也累计增加
            WapForwardBlogVO forwardBlogVO = articleDAO.findForwardBlog(vo.getSourceId());
            if (forwardBlogVO != null){
                articleDAO.updateSetWhereById("read_quantity = read_quantity+1",vo.getSourceId(),null);
            }
            List<FileIndexVO> forwardAttachFileList =fileDAO.findFileListByBizId(vo.getSourceId());
            forwardBlogVO.setFileList(forwardAttachFileList);
            vo.setForwardBlog(forwardBlogVO);
        }
        articleDAO.updateSetWhereById("read_quantity = read_quantity+1",id,null);
        return vo;
    }

    public ResultPage<WapBlogIndexVO> findAllByUserId(WapMyBlogIndexDTO dto){
        ResultPage<WapBlogIndexVO> resultPage = articleDAO.findAllByUserId(dto);
        handleForwardBlogInfo(resultPage);
        return resultPage;
    }

    private void handleForwardBlogInfo(ResultPage<WapBlogIndexVO> resultPage) {
        for (WapBlogIndexVO indexVO : resultPage.getContent()){
            //处理转载的文章
            if (CommonEnum.Is.是.code.shortValue() == indexVO.getIsForward()){
                WapForwardBlogVO forwardBlogVO = articleDAO.findForwardBlog(indexVO.getSourceId());
                List<FileIndexVO> forwardAttachFileList =fileDAO.findFileListByBizId(indexVO.getSourceId());
                forwardBlogVO.setFileList(forwardAttachFileList);
                indexVO.setForwardBlog(forwardBlogVO);
            }
            List<FileIndexVO> attachFileList =fileDAO.findFileListByBizId(indexVO.getId());
            indexVO.setFileList(attachFileList);
            List<WapHotCommentVO> hotCommentVOList = commentDAO.findHotComment(indexVO.getId());
            indexVO.setCommentList(hotCommentVOList);
        }
    }

}
