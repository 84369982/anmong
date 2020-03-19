package com.anmong.common.interceptor;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 接口访问拦截器
 * @author songwenlong
 *
 */
public class AccessInterceptor extends HandlerInterceptorAdapter{
	
	private static Logger log = LoggerFactory.getLogger(AccessInterceptor.class);

	private final static String POST = "POST";

	private final static String GET = "GET";

	private final static String CONTENT_TYPE_JSON = "application/json";
	
	/**
	 *最后执行，可用于释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 *显示视图前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//System.out.println(request.getContentType()+"-----"+request.getCharacterEncoding()+"------"+request.getContextPath());
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     *    因流只能使用一次，重复使用需对request进行包装
     */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		Map<String, String[]> parameter = request.getParameterMap();
		if (POST.equals(request.getMethod())){
			if (CONTENT_TYPE_JSON.equals(request.getContentType())){

				RepeatedlyReadRequestWrapper requestWrapper;
				if (request instanceof RepeatedlyReadRequestWrapper) {
					requestWrapper = (RepeatedlyReadRequestWrapper) request;
					log.info("访问接口:{},参数:{}",url,JSONObject.toJSON(getBodyString(requestWrapper)).toString());
				}

			}
			else {
				log.info("访问接口:{},参数:{}",url,JSONObject.toJSON(parameter).toString());
			}

		}
		if (GET.equals(request.getMethod())){
			log.info("访问接口:{},参数:{}",url,JSONObject.toJSON(parameter).toString());

		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * 获取请求Body
	 *
	 * @param request
	 *
	 * @return
	 */
	public static String getBodyString(final ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = cloneInputStream(request.getInputStream());
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			log.error("转换请求参数出错:{}"+e.getMessage(),e);

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("转换请求参数出错:{}"+e.getMessage(),e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error("转换请求参数出错:{}"+e.getMessage(),e);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Description: 复制输入流</br>
	 *
	 * @param inputStream
	 *
	 * @return</br>
	 */
	public static InputStream cloneInputStream(ServletInputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buffer)) > -1) {
				byteArrayOutputStream.write(buffer, 0, len);
			}
			byteArrayOutputStream.flush();
		} catch (IOException e) {
			log.error("转换请求参数出错:{}"+e.getMessage(),e);
		}
		InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		return byteArrayInputStream;
	}

}
