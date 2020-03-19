package com.anmong.core.service.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dao.UserDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.anmong.common.message.CommonException;
import com.anmong.common.pagination.DataTable;
import com.anmong.core.dto.web.user.WebUserChangePasswordDTO;
import com.anmong.core.dto.web.user.WebUserCreateDTO;
import com.anmong.core.dto.web.user.WebUserIndexDTO;
import com.anmong.core.dto.web.user.WebUserUpdateDTO;
import com.anmong.core.entity.User;
import com.anmong.core.enums.UserEnum;
import com.anmong.core.service.wap.WapAuthService;
import com.anmong.core.service.wap.WapSmsService;
import com.anmong.core.vo.wap.WapUserShowVO;

@Service
public class WebUserService {

	@Autowired
	private WapAuthService wapAuthService;
	@Autowired
	private WapSmsService wapSmsService;
	@Autowired
	private WebFileService webFileService;
	@Autowired
	private UserDAO userDAO;
	
	public  DataTable findAll(HttpServletRequest request,WebUserIndexDTO dto){
		DataTable dt = DataTable.getInstance(request, null);
		dto.setPageNO(dt.getStart()/dt.getLength() + 1);
		dto.setPageSize(dt.getLength());
		ResultPage<User> page = userDAO.findAllUser(dto);
		dt.setData(page.getContent());
		dt.setRecordsTotal(page.getTotalElements());
		dt.setRecordsFiltered(page.getTotalElements());
		return dt;
	}
	
	public void changeState(User entity) {
		userDAO.updateSelective(entity);
    }
	
	public void addUser(WebUserCreateDTO createDTO,String userId) {
		wapSmsService.validate(createDTO.getPhone(), createDTO.getAuthcode());
		wapAuthService.queryUsernameIsExist(createDTO.getUsername());
		wapAuthService.queryPhoneIsExist(createDTO.getPhone());
		User user = new User();
		BeanUtils.copyProperties(createDTO, user);
		user.setState(UserEnum.State.启用.code.shortValue());
		user.setLoginTimes(0);
		user.setLastLogin(new Date());
		user.setCreateAt(new Date());
		user.setCreateMan(userId);
		user.setType(UserEnum.Type.管理员.code.shortValue());
		user.setGrade(new Short("0"));
		userDAO.insert(user);
	}
	
	public WapUserShowVO findOne(String id) {
		WapUserShowVO showVO = new WapUserShowVO();
		User user = userDAO.findOneUserInfo(id);
		BeanUtils.copyProperties(user, showVO);
		return showVO;
		
	}
	
	/**
	 * 更改个人信息
	 * @return
	 */
	public void updateUserInfo(WebUserUpdateDTO updateDTO) {
		//查询旧的用户数据
		User savedUser = userDAO.selectNotNullOneById(updateDTO.getId(),"用户不存在");
		//新上传的头像id
		String newFileId = updateDTO.getFileId();
		User user = new User();
		BeanUtils.copyProperties(updateDTO, user);
		if(!StringUtils.isEmpty(updateDTO.getFileId())) {
			//通过外键来关联用户头像，方便统一管理
			user.setHeadUrl(updateDTO.getFileId());
		}
		userDAO.updateSelective(user);
		if(!StringUtils.isEmpty(updateDTO.getFileId())) {
			//删除过期头像以节约空间;
			webFileService.deleteById(savedUser.getHeadUrl());
			webFileService.updateBizIdByFileId(updateDTO.getId(),newFileId);
			}
	}
	/**
	 * 修改密码
	 * @param updateDTO
	 * @param userId
	 */
	public void changePassword(WebUserChangePasswordDTO updateDTO,String userId) {
		wapSmsService.validate(updateDTO.getPhone(), updateDTO.getAuthcode());
		User savedUser = userDAO.selectNotNullOneById(userId,"用户不存在");
		if(!savedUser.getPassword().equals(updateDTO.getOldPassword())) {
			throw CommonException.businessException("旧密码不正确!");
		}
		userDAO.updateSetWhere("password = ?" ,"id =?",updateDTO.getNewPassword(),userId);
	}
	
}
