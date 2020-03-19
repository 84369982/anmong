package com.anmong.core.web.common;


import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.redis.RedisDao;
import com.anmong.core.service.wap.WapSmsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("public/sms")
public class SmsController {
	
	private static Logger log = LoggerFactory.getLogger(SmsController.class);
	
	@Resource(name="redisDao")
	private RedisDao redisDao;
	
	@Autowired
	private WapSmsService wapSmsService;
	
	@GetMapping("send")
	@ApiOperation(value = "发送短信", tags = "通用-短信")
	public DosserReturnBody send(@RequestParam String phone,
                                 @ApiParam(value = "类型:注册(1)登录(2),找回密码(3),修改密码(4)") @RequestParam  Integer type
			,@RequestParam(required = false) String userId){
		wapSmsService.send(phone,type,userId);
		return new DosserReturnBodyBuilder().statusOk().build();
	} 
	
	@GetMapping("validate")
	@ApiOperation(value = "验证短信正确性", tags = "通用-短信")
	public DosserReturnBody validate(@RequestParam String phone,@RequestParam String authcode){
		wapSmsService.validate(phone, authcode);
		return new DosserReturnBodyBuilder().statusOk().build();
	}

}
