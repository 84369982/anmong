package com.anmong.core.service.wap;

import java.util.Date;

import com.anmong.core.dao.UserDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anmong.common.message.CommonException;
import com.anmong.common.util.ValidateUtil;
import com.anmong.core.dto.wap.user.WapUserCreateDTO;
import com.anmong.core.entity.User;
import com.anmong.core.enums.UserEnum;
import com.anmong.core.vo.wap.WapUserShowVO;

@Service
public class WapAuthService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private WapSmsService wapSmsService;
	
	public WapUserShowVO saveRegister(WapUserCreateDTO createDTO) {
		queryUsernameIsExist(createDTO.getUsername());
		queryPhoneIsExist(createDTO.getPhone());
		User user = new User();
		BeanUtils.copyProperties(createDTO, user);
		user.setState(UserEnum.State.启用.code.shortValue());
		user.setLoginTimes(1);
		user.setLastLogin(new Date());
		user.setCreateAt(new Date());
		user.setType(UserEnum.Type.会员.code.shortValue());
		user.setGrade(new Short("0"));
		userDAO.insert(user);
		WapUserShowVO showVO = new WapUserShowVO();
		BeanUtils.copyProperties(user, showVO);
		return showVO;
	}
	
	/**
	 * 先根据username查，如果查不到，再根据电话号码查
	 * @param account
	 * @param password
	 * @return
	 */
	public WapUserShowVO loginByAccount(String account,String password) {
		User user = userDAO.selectOneWhere("username = ? AND password =?",account,password);
		WapUserShowVO showVO = new WapUserShowVO(); 
		if(user != null) {
			isDisabled(user);
			user = userDAO.findOneUserInfo(user.getId());
			BeanUtils.copyProperties(user, showVO);
			return showVO;
		}
		else {
			user = userDAO.selectOneWhere("phone = ? AND password =?",account,password);
			if(user != null) {
				isDisabled(user);
				user = userDAO.findOneUserInfo(user.getId());
				BeanUtils.copyProperties(user, showVO);
				return showVO;
			}
			else {
				throw CommonException.businessException("用户名或密码错误!");
			}
		}
		
	}
	
	public WapUserShowVO loginByPhone(String phone,String authcode) {
		if (wapSmsService.validate(phone, authcode)) {
			User user = userDAO.selectOneWhere("phone = ?",phone);
			WapUserShowVO showVO = new WapUserShowVO(); 
			if(user != null) {
				isDisabled(user);
				user = userDAO.findOneUserInfo(user.getId());
				BeanUtils.copyProperties(user, showVO);
				return showVO;
			}
			else {
				throw CommonException.businessException("登录出错，请仔细检查手机号!");
			}
		}
		else {
			throw CommonException.businessException("登录出错，请仔细检查手机号!");
		}
		
	}
	
	public void updateFoundPassword(String phone,String authcode,String password) {
		if (wapSmsService.validate(phone, authcode)) {
			User user = userDAO.selectOneWhere("phone = ?",phone);
			if(user == null) {
				throw CommonException.businessException("该手机号还没有注册!");
			}
			isDisabled(user);
			userDAO.updateSetWhere("password = ?" ,"id =?",password,user.getId());
		}
	}
	
	
	
	
	public void queryUsernameIsExist(String username) {
		User user = userDAO.selectOneWhere("username = ?",username);
		if(user != null) {
			throw CommonException.businessException("该账号已经被注册!");
		}
	}
	
	public void queryPhoneIsExist(String phone) {
		if(!ValidateUtil.isPhone(phone)) {
			throw CommonException.businessException("请输入正确的手机号!");
		}
		User user = userDAO.selectOneWhere("phone = ?" ,phone);
		if(user != null) {
			throw CommonException.businessException("该手机号已经被注册!");
		}
	}
	
	public void isDisabled(User user) {
		if(UserEnum.State.禁用.code.shortValue() == user.getState().shortValue()) {
			throw CommonException.businessException("该账号已经被封禁!");
		}
		
	}
	

}
