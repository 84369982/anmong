package com.anmong.core.web.wap;

import javax.validation.Valid;


import com.anmong.core.dto.wap.user.WapBlogUserShowDTO;
import com.anmong.core.dto.wap.user.WapUserSearchDTO;
import com.anmong.core.vo.wap.user.WapBlogUserShowVO;
import com.anmong.core.vo.wap.user.WapUserSearchVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.wap.user.WapUserUpdateDTO;
import com.anmong.core.service.wap.WapUserService;

@RestController
@RequestMapping("wap/user")
public class WapUserController {
	

	@Autowired
	private WapUserService userService;
	
	@PostMapping("change-head-url")
	@ApiOperation(value = "更改头像", tags = "wap-用户")
	public DosserReturnBody changeHeadUrl(@RequestParam String userId,@RequestParam String fileId) {
		userService.changeHeadUrl(userId, fileId);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@PostMapping("update-user-info")
	@ApiOperation(value = "修改个人信息", tags = "wap-用户")
	public DosserReturnBody changeUserInfo(@Valid WapUserUpdateDTO updateDTO) {
		userService.updateUserInfo(updateDTO);
		return new DosserReturnBodyBuilder().statusOk().build();
	}

	@GetMapping("find-by-user-id")
	@ApiOperation(value = "根据id查询博客信息",tags = "wap-用户",response = WapBlogUserShowVO.class)
	public DosserReturnBody findBlogUserInfo(@Valid WapBlogUserShowDTO dto){
		return new DosserReturnBodyBuilder().collectionItem(userService.findBlogUserInfo(dto)).build();
	}

	@GetMapping("find-by-nickname")
	@ApiOperation(value = "搜索用户",tags = "wap-用户",response = WapUserSearchVO.class)
	public DosserReturnBody findUserByNickname(@Valid WapUserSearchDTO dto){
		return new DosserReturnBodyBuilder().collection(userService.userSearch(dto)).build();
	}




}
