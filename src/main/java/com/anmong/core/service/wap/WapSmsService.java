package com.anmong.core.service.wap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.anmong.core.dao.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anmong.common.config.MyConfig;
import com.anmong.common.message.CommonException;
import com.anmong.common.redis.RedisDao;
import com.anmong.common.util.DateUtil;
import com.anmong.common.util.HttpUtil;
import com.anmong.common.util.RandomUtil;
import com.anmong.common.util.ValidateUtil;
import com.anmong.core.entity.User;
import com.anmong.core.enums.SmsEnum;

@Service
@Slf4j
public class WapSmsService {
	
	@Resource(name="redisDao")
	private RedisDao redisDao;


	@Autowired
	private UserDAO userDAO;
	
	public void send(String phone,Integer type,String userId) {
		if(!ValidateUtil.isPhone(phone)) {
			throw CommonException.businessException("请输入正确的手机号!");
		}
		List<HashMap<String,Object>> authcodeList = (List<HashMap<String, Object>>) redisDao.get(phone);
		if(authcodeList != null && authcodeList.size() >= Integer.parseInt(MyConfig.getConfig("sms.maxSendOneDay")) ) {
			throw CommonException.businessException("该手机号今天发短信过于频繁，请明天再试！");
		}
		User user = userDAO.selectOneWhere("phone = ?",phone);
		if(SmsEnum.Type.注册.code.equals(type)) {
			if(user == null) {
				sendSms(phone,authcodeList);
			}
			else {
				throw CommonException.businessException("该手机号已被注册!");
			}
		}
		else if(SmsEnum.Type.修改密码.code.equals(type)) {
			if(user != null) {
				User savedUser = userDAO.selectNotNullOneById(userId,"用户不存在!");
				if(savedUser.getPhone().equals(phone)) {
					sendSms(phone,authcodeList);
				}
				else {
					throw CommonException.businessException("请使用注册该账号的手机号来验证!");
				}
				
			}
			else {
				throw CommonException.businessException("该手机号还未注册!");
			}
		}
		else {
			if(user != null) {
				sendSms(phone,authcodeList);
			}
			else {
				throw CommonException.businessException("该手机号还未注册!");
			}
		}
		
		
		
	}
	
	public boolean validate(String phone,String authcode) {
		if(!ValidateUtil.isPhone(phone)) {
			throw CommonException.businessException("请输入正确的手机号!");
		}
		List<HashMap<String,Object>> authcodeList = (List<HashMap<String, Object>>) redisDao.get(phone);
		if(authcodeList == null) {
			throw CommonException.businessException("验证码不正确!");
		}
		if(!authcodeList.isEmpty() && authcodeList.size() > 0) {
		   HashMap<String, Object> authcodeMap = authcodeList.get(authcodeList.size()-1);
		   long time = Long.parseLong(authcodeMap.get("time").toString());
		   if((System.currentTimeMillis()/1000 - time ) > Long.parseLong(MyConfig.getConfig("sms.expire"))) {
			   throw CommonException.businessException("验证码已过期");
		   }
		   if(authcode.equals(authcodeMap.get("authcode").toString())) {
			   return true;
		   }
		   else {
			   throw CommonException.businessException("验证码不正确!");
		   }
		}
		else {
			throw CommonException.businessException("验证码不正确!");
		}
		
	}
	
	public void sendSms(String phone,List<HashMap<String,Object>> authcodeList) {
		Map<String, Object> params = new HashMap<>();
		params.put("action", "send");
		params.put("account", MyConfig.getConfig("sms.account"));
		params.put("password", MyConfig.getConfig("sms.passwordMD5"));
		params.put("mobile", phone);
		String authcode = RandomUtil.getFourRandom();
		params.put("content", "验证码："+authcode+"【安梦网】");
		params.put("sendTime", "");
		params.put("extno", "");
		if(Boolean.parseBoolean(MyConfig.getConfig("sms.isSend"))) {
			HttpUtil.post(MyConfig.getConfig("sms.url").toString(), params);
		}
		log.info(phone+"验证码:"+authcode);
		HashMap<String, Object> authcodeMap = new HashMap<>();
		authcodeMap.put("authcode", authcode);
		authcodeMap.put("time", System.currentTimeMillis()/1000);
		redisDao.saveElementsToList(phone, authcodeMap);
		if(authcodeList == null) {
			redisDao.expireAt(phone, DateUtil.getTodayEnd());
		}
	}
	

}
