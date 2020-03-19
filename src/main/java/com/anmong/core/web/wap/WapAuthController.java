package com.anmong.core.web.wap;

import javax.jws.WebParam;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.wap.user.WapUserCreateDTO;
import com.anmong.core.entity.User;
import com.anmong.core.service.wap.WapAuthService;
import com.anmong.core.service.wap.WapUserService;
import com.anmong.core.vo.wap.WapUserShowVO;

@RestController
@RequestMapping("wap/auth")
public class WapAuthController {
	
	@Autowired
	private WapAuthService wapAuthService;

	@PostMapping(value = "register")
	@ApiOperation(value = "注册", tags = "wap-用户", response = WapUserShowVO.class)
	public DosserReturnBody register(@Valid WapUserCreateDTO createDTO) {
		return new DosserReturnBodyBuilder().collectionItem(wapAuthService.saveRegister(createDTO)).build();
	}

	@GetMapping("query_username_is_exist")
	@ApiOperation(value = "查询用户是否存在", tags = "wap-用户")
	public DosserReturnBody queryUsernameIsExist(@RequestParam String username) {
		wapAuthService.queryUsernameIsExist(username);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@GetMapping("query_phone_is_exist")
	@ApiOperation(value = "查询电话是否已被注册", tags = "wap-用户")
	public DosserReturnBody queryPhoneIsExist(@RequestParam String phone) {
		wapAuthService.queryPhoneIsExist(phone);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@GetMapping("login_by_account")
	@ApiOperation(value = "使用用户名登录", tags = "wap-用户")
	public DosserReturnBody loginByAccount(@RequestParam String account,@RequestParam String password) {
		return new DosserReturnBodyBuilder().collectionItem(wapAuthService.loginByAccount(account, password)).build();
	}
	
	@GetMapping("login_by_phone")
	@ApiOperation(value = "使用电话登录", tags = "wap-用户")
	public DosserReturnBody loginByPhone(@RequestParam String phone,@RequestParam String authcode) {
		return new DosserReturnBodyBuilder().collectionItem(wapAuthService.loginByPhone(phone, authcode)).build();
	}
	
	@PostMapping("found_password")
	@ApiOperation(value = "找回密码", tags = "wap-用户")
	public DosserReturnBody foundPassword(@RequestParam String phone,@RequestParam String authcode,@RequestParam String password) {
		wapAuthService.updateFoundPassword(phone, authcode, password);
		return new DosserReturnBodyBuilder().statusOk().build();
	}

}
