package com.anmong.core.service.wap;


import com.anmong.core.dao.AttentionDAO;
import com.anmong.core.dao.UserDAO;
import com.anmong.core.dto.wap.user.WapBlogUserShowDTO;
import com.anmong.core.dto.wap.user.WapUserSearchDTO;
import com.anmong.core.vo.wap.user.WapBlogUserShowVO;
import com.anmong.core.vo.wap.user.WapUserSearchVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.anmong.core.dto.wap.user.WapUserUpdateDTO;
import com.anmong.core.entity.User;
import com.anmong.core.service.web.WebFileService;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class WapUserService {

	@Autowired
	private WebFileService webFileService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AttentionDAO attentionDAO;

	
	/**
	 * 更改头像
	 * @return
	 */
	public void changeHeadUrl(String id,String fileId) {
		//查询旧的用户数据
		User savedUser = userDAO.selectNotNullOneById(id,"用户不存在");
		userDAO.updateSetWhere("head_url = ?" ,"id =?",fileId,id);;
		//删除过期头像以节约空间;
		if(savedUser != null ) {
			webFileService.deleteById(savedUser.getHeadUrl());
			webFileService.updateBizIdByFileId(id,fileId);
			
		}
	}
	
	/**
	 * 更改个人信息
	 * @return
	 */
	public void updateUserInfo(WapUserUpdateDTO updateDTO) {
		User user = new User();
		BeanUtils.copyProperties(updateDTO, user);
	    userDAO.updateSelective(user);
	}

	public WapBlogUserShowVO findBlogUserInfo(WapBlogUserShowDTO dto){
		WapBlogUserShowVO vo = userDAO.findBlogUserInfo(dto.getUserId());
		//查询关注数
		int attention = attentionDAO.countWhere("create_man = ?",dto.getUserId());
		vo.setAttention(attention);
		//查询是否关注.
		if (!StringUtils.isEmpty(dto.getId())){
			int isAttention = attentionDAO.countWhere("user_id = ? AND create_man = ?",dto.getUserId(),dto.getId());
			vo.setIsAttention(isAttention > 0 ? true : false);
		}

		return vo;
	}

	public List<WapUserSearchVO> userSearch(WapUserSearchDTO dto){
		return userDAO.userSearch(dto);
	}

	

}
