package com.anmong.common.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.anmong.common.config.MyConfig;

/**
 * @author yan
 * @date 2016-6-9 11:03:04
 * @version V1.0
 * @desc
 */
public class HttpUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static final String get(final String url, final Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("");

        if (null != params && !params.isEmpty()) {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key).append("=").append(params.get(key));
                i++;
            }
        }

        CloseableHttpClient httpClient = createSSLClientDefault();

        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(url + sb.toString());
        String result = "";

        try {
            response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (IOException ex) {
           // Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
        	
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    //Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    public static final JSONObject post(final String url, final Map<String, Object> params) {
    	
        CloseableHttpClient httpClient = createSSLClientDefault();
        HttpPost post = new HttpPost(url);

        CloseableHttpResponse response = null;

        if (null != params && !params.isEmpty()) {
            List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                nvpList.add(nvp);
            }
            post.setEntity(new UrlEncodedFormEntity(nvpList, Charset.forName("UTF-8")));
        }
        JSONObject result = null;
        try {
            response = httpClient.execute(post);
            
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                	result = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
                	//不打印新闻接口的请求结果
                	if(MyConfig.getConfig("news.listUrl").equals(url)
                			|| MyConfig.getConfig("news.channelUrl").equals(url)
                			|| MyConfig.getConfig("news.searchUrl").equals(url)
                			) {
                		log.info("请求成功:{}",url);
                	}
                	else {
                		log.info("请求成功:{}",result.toJSONString());
                	}
                	
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    //Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    private static CloseableHttpClient createSSLClientDefault() {
    	
    	

        SSLContext sslContext;
        try {
        	sslContext = SSLContext.getInstance("SSL");
        	sslContext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            //Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return HttpClients.createDefault();
    }
    
    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
   

 
}