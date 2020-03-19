package com.anmong.core.web;

import com.alibaba.fastjson.JSONObject;
import com.anmong.common.util.HttpsUtil;
import com.anmong.core.dao.UserDAO;
import com.anmong.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class WXController {

    @Autowired
    private UserDAO userDAO;

    public final static String TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";


    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};



    @GetMapping("/config")
    public JSONObject config() {

        Map<String,Object> parameter = new HashMap<>();
        parameter.put("appid","123");
        parameter.put("secret","456");
        parameter.put("code","789");
        parameter.put("grant_type","authorization_code");
        JSONObject jsonObject = HttpsUtil.post(TOKEN_URL,parameter);
        return jsonObject;


    }

    @GetMapping("/test")
    public List<User> test() {
        return userDAO.selectWhere("1 = ?", 1);
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
