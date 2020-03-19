package com.anmong.core.web;


import com.alibaba.fastjson.JSONObject;
import com.anmong.common.util.HttpsUtil;
import com.anmong.common.util.MapFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonObject;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/public")
public class WechatController {

    public  final static String APP_ID = "wxc222553fe48c6d0c";

    public  final static String APP_SECRET = "e136d83fa0ec9932a55a6a4a3fb315c1";

    //public  static String CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=redirect_uri&response_type=code&scope=snsapi_userinfo&state=2#wechat_redirect";

    @GetMapping("get-auth-url")
    @ApiOperation(value = "获取授权回调的地址",tags = "微信接口")
    public String getCode(String url){
        String codeUrl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                APP_ID, url, "snsapi_userinfo", "xxxx_state");
        return codeUrl;
    }

    @GetMapping("get-user-info")
    @ApiOperation(value = "获取用户信息",tags = "微信接口")
    public JSONObject getUsrInfo(String code){
        //首先获取access_token
        String tokenUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                APP_ID, APP_SECRET, code);
        JSONObject tokenResult = HttpsUtil.get(tokenUrl,null);
        String accessToken = tokenResult.getString("access_token");
        String openId = tokenResult.getString("openid");
        //再根据token和openId获取用户信息
        String userUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        return HttpsUtil.post(userUrl,null);
    }


    /**
     *
     * @param url 要使用jssdk的页面url
     * @return
     */
    @GetMapping("get-config")
    @ApiOperation(value = "获取jssdk配置",tags = "微信接口")
    public Map<String, String> getConfig(String url){

        return sign(getJsapiTicket(),url);
    }




    /**
     * 创建自定义菜单
     */
    @GetMapping("create-menu")
    @ApiOperation(value = "创建自定义菜单",tags = "微信接口")
    public JSONObject createMenu(String url){
        String token = getAccessToken();
        //先删除自定义菜单
        HttpsUtil.get("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+token,null);
        String menu = "{\"button\": [{\"type\": \"view\",\"name\": \"获取用户信息\",\"url\":\""+url+"\"}]}";
        //创建菜单
        return HttpsUtil.postJson("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token,menu);
    }

    ////////////////以下为工具方法、、、、、、、、、、、、、、、

    /**
     * 获取基础token，与网页授权token不同，不能通用，暂未做缓存。
     * @return
     */
    private String getAccessToken(){
        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,Object> tokenParam = new HashMap<>();
        tokenParam.put("appid",APP_ID);
        tokenParam.put("secret",APP_SECRET);
        tokenParam.put("grant_type","client_credential");
        String token = HttpsUtil.get(tokenUrl,tokenParam).getString("access_token");
        return token;
    }

    /**
     * 获取基础JsapiTicket，暂未做缓存。
     * @return
     */
    private String getJsapiTicket(){
        String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
        Map<String,Object> tokenParam = new HashMap<>();
        tokenParam.put("access_token",getAccessToken());
        tokenParam.put("type","jsapi");
        String ticket = HttpsUtil.get(jsapiTicketUrl,tokenParam).getString("ticket");
        return ticket;
    }


    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", APP_ID);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


}
