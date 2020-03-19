package com.anmong.core.web.wap;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.anmong.common.config.MyConfig;
import com.anmong.common.util.HttpUtil;

@RestController
@RequestMapping("wap/news")
public class NewsController {
	

	@GetMapping("list")
	@ApiOperation(value = "随机查询新闻列表数据", tags = "wap-新闻")
	public JSONObject findALlByLimit(String channel,String num,String start){
		Map<String, Object> params = new HashMap<>();
		params.put("appkey", MyConfig.getConfig("news.appkey"));
		params.put("channel", channel);
		params.put("num", num);
		params.put("start", start);
		JSONObject json = HttpUtil.post(MyConfig.getConfig("news.listUrl").toString(), params);
		return json;
	} 
	
	@GetMapping("channel")
	@ApiOperation(value = "查询所有频道", tags = "wap-新闻")
	public JSONObject findAllChannel(){
		Map<String, Object> params = new HashMap<>();
		params.put("appkey", MyConfig.getConfig("news.appkey"));
		JSONObject json = HttpUtil.post(MyConfig.getConfig("news.channelUrl").toString(), params);
		return json;
	} 
	
	@GetMapping("search")
	@ApiOperation(value = "搜索新闻", tags = "wap-新闻")
	public JSONObject search(String keyword){
		Map<String, Object> params = new HashMap<>();
		params.put("appkey", MyConfig.getConfig("news.appkey"));
		params.put("keyword", keyword);
		JSONObject json = HttpUtil.post(MyConfig.getConfig("news.searchUrl").toString(), params);
		return json;
	} 
}
