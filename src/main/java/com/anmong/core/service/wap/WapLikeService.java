package com.anmong.core.service.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dao.ArticleDAO;
import com.anmong.core.dao.CommentDAO;
import com.anmong.core.dao.LikeDAO;
import com.anmong.core.dto.wap.like.WapLikeCreateDTO;
import com.anmong.core.dto.wap.like.WapLikeIndexDTO;
import com.anmong.core.entity.Like;
import com.anmong.core.enums.LikeEnum;
import com.anmong.core.vo.wap.like.WapLikeIndexVO;
import com.anmong.core.vo.wap.like.WapLikeResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author songwenlong
 * 2018/3/12
 */
@Service
public class WapLikeService {

    @Autowired
    private LikeDAO likeDAO;
    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private CommentDAO commentDAO;

    public WapLikeResultVO add(WapLikeCreateDTO dto){
        WapLikeResultVO vo = new WapLikeResultVO();
        //判断是点赞还是取消点赞
        Like existLike = likeDAO.selectOneWhere("biz_id = ? AND create_man = ? AND type = ?",dto.getBizId()
                ,dto.getCreateMan(),dto.getType());
        if (existLike != null){
            likeDAO.deleteOneById(existLike.getId());
            vo.setResult(LikeEnum.Result.取消点赞.code.shortValue());
            if (LikeEnum.Type.文章点赞.code.shortValue() == dto.getType()){
                articleDAO.updateSetWhereById("like_quantity = like_quantity - 1",dto.getBizId(),null);
            }
            if (LikeEnum.Type.评论点赞.code.shortValue() == dto.getType()){
                commentDAO.updateSetWhereById("like_quantity = like_quantity - 1",dto.getBizId(),null);
            }
        }
        else {
            Like like = new Like();
            BeanUtils.copyProperties(dto,like);
            like.setCreateAt(new Date());
            likeDAO.insert(like);
            vo.setResult(LikeEnum.Result.点赞.code.shortValue());
            if (LikeEnum.Type.文章点赞.code.shortValue() == dto.getType()){
                articleDAO.updateSetWhereById("like_quantity = like_quantity + 1",dto.getBizId(),null);
            }
            if (LikeEnum.Type.评论点赞.code.shortValue() == dto.getType()){
                commentDAO.updateSetWhereById("like_quantity = like_quantity + 1",dto.getBizId(),null);
            }

        }
        return vo;
    }

    public ResultPage<WapLikeIndexVO> findAllLikeInfo(WapLikeIndexDTO dto){
        return likeDAO.findAllLikeInfo(dto);
    }
}
