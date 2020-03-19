package com.anmong.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.anmong.common.config.MyConfig;
import com.anmong.common.message.CommonException;
import com.anmong.core.enums.FileEnum;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.NOSException;
import com.netease.cloud.services.nos.model.ObjectMetadata;

public class UploadUtil {
	
	private static Logger log = LoggerFactory.getLogger(UploadUtil.class);
	
	public static void uploadSelector(Integer storeType,InputStream inputStream,String uploadUrl,HttpServletRequest request) {
		if(FileEnum.StoreType.腾讯云1.code.equals(storeType)) {
	        // 设置要操作的bucket
	        String bucketName = MyConfig.getConfig("thirdparty.upload.tencent.1.bucketName");
			uploadToTencentCOS(initTencentCOS1(), bucketName,inputStream,uploadUrl);
		}
		if(FileEnum.StoreType.腾讯云2.code.equals(storeType)) {
			 // 设置要操作的bucket
	        String bucketName = MyConfig.getConfig("thirdparty.upload.tencent.2.bucketName");
			uploadToTencentCOS(initTencentCOS2(), bucketName,inputStream,uploadUrl);
		}
		if(FileEnum.StoreType.网易云.code.equals(storeType)) {
			String bucketName = MyConfig.getConfig("thirdparty.upload.163.1.bucketName");
			uploadTo163NOS(init163NOS1(),bucketName, uploadUrl, inputStream);
		}
		if(FileEnum.StoreType.本地.code.equals(storeType)) {
			saveFileToLocal(uploadUrl,inputStream,request);
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
  			throw CommonException.businessException("上传时发生了错误，请重新上传!");
		}
	}
	
	public static void deleteSelector(Integer storeType,String path) {
		if(FileEnum.StoreType.网易云.code.equals(storeType)) {
			String bucketName = MyConfig.getConfig("thirdparty.upload.163.1.bucketName");
			deleteFrom163NOS(init163NOS1(),bucketName,path);
		}
		if(FileEnum.StoreType.腾讯云1.code.equals(storeType)) {
			String bucketName = MyConfig.getConfig("thirdparty.upload.tencent.1.bucketName");
			deleteFromTencentCOS(initTencentCOS1(),bucketName,path);
		}
		if(FileEnum.StoreType.腾讯云2.code.equals(storeType)) {
			String bucketName = MyConfig.getConfig("thirdparty.upload.tencent.2.bucketName");
			deleteFromTencentCOS(initTencentCOS2(),bucketName,path);
		}
		if(FileEnum.StoreType.本地.code.equals(storeType)) {
			deleteFromLocalPath(path);
		}
		
	}
	
	private static void uploadToTencentCOS(COSClient cosClient,String bucketName
			,InputStream inputStream,String uploadUrl) {
     
        ///////////////////////////////////////////////////////////////
        // 文件操作 //
        ///////////////////////////////////////////////////////////////
        // 1. 上传文件(默认不覆盖)
        // 将本地的local_file_1.txt上传到bucket下的根分区下,并命名为sample_file.txt
        // 默认不覆盖, 如果cos上已有文件, 则返回错误
       
    	try {
    		byte[] buffer = IOUtils.toByteArray(inputStream);
  	        UploadFileRequest uploadFileRequest =
  	                new UploadFileRequest(bucketName, uploadUrl, buffer);
  	        uploadFileRequest.setEnableShaDigest(false);
  	        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
  	        cosClient.shutdown();
  	        JSONObject json = JSONObject.parseObject(uploadFileRet);
  	        if(!"0".equals(String.valueOf(json.get("code")))) {
  	        	log.error(json.getString("message"));
  	        	throw CommonException.businessException("上传时发生了错误，请重新上传!");
  	        }
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			log.error(e.getMessage());
  			throw CommonException.businessException("上传时发生了错误，请重新上传!");
  		}
 
		
	}
	
	private static void uploadTo163NOS(NosClient nosClient,String bucketName,String uploadUrl
			,InputStream inputStream) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		try {
			objectMetadata.setContentLength(inputStream.available());
			String suffix = uploadUrl.substring(uploadUrl.lastIndexOf(".")+1,uploadUrl.length());
			objectMetadata.setContentType(FileUtil.GetContentType(suffix));
			//去掉"/",网易云不支持/开头
			uploadUrl = uploadUrl.substring(uploadUrl.indexOf("/")+1,uploadUrl.length());
			nosClient.putObject(bucketName,uploadUrl,inputStream,objectMetadata);
			nosClient.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
  			throw CommonException.businessException("上传时发生了错误，请重新上传!");
		} catch (NOSException e) {
			// TODO: handle exception
			log.error(e.getMessage());
  			throw CommonException.businessException("上传时发生了错误，请重新上传!");
		}
		
		
	}
	
    /**
     * 上传文件到本地
     */
	private static void saveFileToLocal(String uploadUrl,InputStream inputStream,
    		HttpServletRequest request) {
    	String uploadPath = MyConfig.getConfig("system.file.uploadLocalPath");
    	String uploadFolder = uploadPath+uploadUrl.substring(0,uploadUrl.lastIndexOf("/"));
        String localPath =  request.getSession().getServletContext().getRealPath("/") 
         		+ uploadFolder;
        File saveDirecotory = new File(localPath);
        if(!saveDirecotory.isDirectory() ) {
        	saveDirecotory.mkdirs();
        }
       
        String saveFilePath = request.getSession().getServletContext().getRealPath("/") +uploadPath.substring(1,uploadPath.length())+uploadUrl;
    
        File saveFile = new File(saveFilePath);
        try {
    		//设置权限为不可执行，保证系统安全
    		saveFile.setExecutable(false);
    		OutputStream os = new FileOutputStream(saveFile);
    		int bytesRead = 0;
    		byte[] buffer = new byte[8192];
    		while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
    		os.write(buffer, 0, bytesRead);
    		}
    		os.close();
    		inputStream.close();       
        } catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			log.error("上传失败:"+e.getMessage());
			throw CommonException.businessException("上传时发生了错误，请重新上传!");
		}  
    }
    
	private static void deleteFrom163NOS(NosClient nosClient,String bucketName,String path) {
		//去掉"/",网易云不支持/开头
		path = path.substring(path.indexOf("/")+1,path.length());
		if(nosClient.doesObjectExist(bucketName,path)) {
			try {
				nosClient.deleteObject(bucketName,path);
			} catch (NOSException e) {
				// TODO: handle exception
				log.error(e.getMessage());
				throw CommonException.businessException("删除失败!");
			}
    	}
		nosClient.shutdown();
    }
	
	private static void deleteFromTencentCOS(COSClient cosClient,String bucketName,String path) {
		DelFileRequest delFileRequest = new DelFileRequest(bucketName, path);
		String delFileRet = cosClient.delFile(delFileRequest);
		cosClient.shutdown();
        JSONObject json = JSONObject.parseObject(delFileRet);
        if(!"0".equals(String.valueOf(json.get("code")))) {
        	log.error(json.getString("message"));
        	throw CommonException.businessException("删除失败!");
        }
	}
	
	private static void deleteFromLocalPath(String path) {
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}
	}
    
    /**
     * 初始化网易云客户端
     */
    private static NosClient init163NOS1() {
    	String accessKey = MyConfig.getConfig("thirdparty.upload.163.1.accessKey");
		String secretKey = MyConfig.getConfig("thirdparty.upload.163.1.accessSecret");
		String endPoint = MyConfig.getConfig("thirdparty.upload.163.1.endPoint");
		com.netease.cloud.auth.Credentials credentials = new BasicCredentials(accessKey, secretKey);
		NosClient nosClient = new NosClient(credentials);
		nosClient.setEndpoint(endPoint);
		return nosClient;
    }
    
    private static COSClient initTencentCOS1() {
    	// 设置用户属性, 包括appid, secretId和SecretKey
        // 这些属性可以通过cos控制台获取(https://console.qcloud.com/cos)
        long appId = Long.parseLong(MyConfig.getConfig("thirdparty.upload.tencent.1.appId"));
        String secretId = MyConfig.getConfig("thirdparty.upload.tencent.1.secretId");
        String secretKey = MyConfig.getConfig("thirdparty.upload.tencent.1.secretKey");
     // 设置bucket所在的区域，比如广州(gz), 天津(tj)
        String region = MyConfig.getConfig("thirdparty.upload.tencent.1.region");
    	// 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(region);
        // 初始化秘钥信息
        Credentials cred = new Credentials(appId, secretId, secretKey);
        // 初始化cosClient
        COSClient cosClient = new COSClient(clientConfig, cred);
        return cosClient;
    }
    
    private static COSClient initTencentCOS2() {
    	// 设置用户属性, 包括appid, secretId和SecretKey
        // 这些属性可以通过cos控制台获取(https://console.qcloud.com/cos)
        long appId = Long.parseLong(MyConfig.getConfig("thirdparty.upload.tencent.2.appId"));
        String secretId = MyConfig.getConfig("thirdparty.upload.tencent.2.secretId");
        String secretKey = MyConfig.getConfig("thirdparty.upload.tencent.2.secretKey");
     // 设置bucket所在的区域，比如广州(gz), 天津(tj)
        String region = MyConfig.getConfig("thirdparty.upload.tencent.2.region");
    	// 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(region);
        // 初始化秘钥信息
        Credentials cred = new Credentials(appId, secretId, secretKey);
        // 初始化cosClient
        COSClient cosClient = new COSClient(clientConfig, cred);
        return cosClient;
    }
    

	
	

}
