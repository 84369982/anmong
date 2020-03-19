package com.anmong.core.web.common;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.util.VerificationCodeUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author songwenlong
 * 2018/2/28
 */
@RestController
@RequestMapping("public/authcode")
public class VerificationCodeController {

    @RequestMapping(value="get-verification-code",method = RequestMethod.GET)
    @ApiOperation(value = "获取图片验证码", tags = "通用-验证码")
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        VerificationCodeUtils.createVerifyCode(request,response);
    }

    @RequestMapping(value="validate",method = RequestMethod.GET)
    @ApiOperation(value = "验证验证码正确性", tags = "通用-验证码")
    public DosserReturnBody getVerificationCode(HttpServletRequest request, @RequestParam String authcode) {
        return  new DosserReturnBodyBuilder().collectionItem(VerificationCodeUtils.checkVerificationCode(request,authcode)).build();
    }
}
