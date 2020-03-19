package com.anmong.common.util;

import com.alibaba.fastjson.JSONObject;
import com.anmong.common.message.CommonException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpsUtil {

    private static Logger log = LoggerFactory.getLogger(HttpsUtil.class);

    public static JSONObject get(String url ,Map<String,Object> parameter) {
        StringBuilder sb = new StringBuilder("");
        if (null != parameter && !parameter.isEmpty()) {
            int i = 0;
            for (String key : parameter.keySet()) {
                if (i == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key).append("=").append(parameter.get(key));
                i++;
            }
        }
        log.info("发送https get请求:{}", url + sb.toString());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url + sb.toString());
        CloseableHttpResponse httpResponse = null;
        StringBuffer response = null;
        try {
            httpResponse = httpClient.execute(httpGet);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
            httpClient.close();
        } catch (IOException e) {
            log.info("https get请求出错{}",e.getMessage(),e);
            throw CommonException.businessException("网络错误!");
        }
        JSONObject result = (JSONObject) JSONObject.parse(response.toString());
        log.info("https get请求结果:{}",result.toJSONString());
        return result;
    }

    public static JSONObject post(String url, Map<String,Object> parameter) {
        log.info("发送https post请求:{}", url );
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (null != parameter && !parameter.isEmpty()) {
            List<NameValuePair> nvpList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : parameter.entrySet()) {
                NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                nvpList.add(nvp);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvpList, Charset.forName("UTF-8")));
        }
        StringBuffer response = null;
        try {
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));
        String inputLine;
        response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        httpClient.close();
        } catch (IOException e) {
            log.info("https post请求出错{}",e.getMessage(),e);
            throw CommonException.businessException("网络错误!");
        }
        JSONObject result = (JSONObject) JSONObject.parse(response.toString());
        log.info("https post请求结果:{}",result.toJSONString());
        return result;
    }

    public static JSONObject postJson(String url, String json) {
        log.info("发送https post请求:{}", url );
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
        StringBuffer response = null;
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
            httpClient.close();
        } catch (IOException e) {
            log.info("https post请求出错{}",e.getMessage(),e);
            throw CommonException.businessException("网络错误!");
        }
        JSONObject result = (JSONObject) JSONObject.parse(response.toString());
        log.info("https post请求结果:{}",result.toJSONString());
        return result;
    }

}
