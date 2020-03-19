package com.anmong.core.web.common;

import com.anmong.common.message.CommonException;
import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.util.DateUtil;
import com.anmong.common.util.FileUtil;
import com.anmong.common.util.MapFactory;
import com.anmong.common.util.RandomUtil;
import com.anmong.core.dto.wap.file.WapFileUploadDTO;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.enums.ModuleEnum;
import com.anmong.core.service.web.WebFileService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload/file")
public class PublicFileController {
	
	private static Logger log = LoggerFactory.getLogger(PublicFileController.class);
	
	@Autowired
	private WebFileService webFileService;
	
	   /* @PostMapping("/upload")  
	    public DosserReturnBody upload(HttpServletRequest request,@Valid WapFileUploadDTO upload) {
	    	List<String> urlList = new ArrayList<>();
	    	List<com.anmong.core.entity.File> fileList = new ArrayList<>();
	        //创建一个通用的多部分解析器  
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        //判断 request 是否有文件上传,即多部分请求  
	        if(multipartResolver.isMultipart(request)){  
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            //取得request中的所有文件名  
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	                //记录上传过程起始时的时间，用来计算上传时间  
	                long pre = System.currentTimeMillis();  
	                //取得上传文件  
	                MultipartFile file = multiRequest.getFile(iter.next());  
	                if(file != null){  
	                    //取得当前上传文件的文件名称  
	                	String  originalFileName= file.getOriginalFilename(); 
	                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
	                    if(originalFileName.trim() !=""){  
	                        String module = ModuleEnum.Name.getCode(upload.getModule());
	                        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".")+1,originalFileName.length());
	                        //重命名上传后的文件名  
	                        String newFileName = module+"_"+DateUtil.getYYYYmmDDhhMMssSSS(new Date())+RandomUtil.getFourRandom()+"."+suffix;
	                        com.anmong.core.entity.File myFile = new com.anmong.core.entity.File();
	                        myFile.setNewFileName(newFileName);
	                        myFile.setModuleCode(module);
	                        myFile.setType(upload.getType());
	                        if(FileEnum.FileType.图片.code.intValue() == upload.getType().intValue()) {
	                        	if(!FileUtil.allowUploadImgTypeAndSize(suffix,file.getSize())) {
	                        		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxImgSize").toString());
	    	                		throw CommonException.businessException("文件不合法!请上传图片文件，且大小不超过"+allowSize/1024000L+"M！");
	    	                	}
	                        	myFile.setStoreType(FileEnum.StoreType.本地.code.shortValue());
	                        	saveFileToLocal(file,myFile,request,urlList);
	                        }
	                        if(FileEnum.FileType.视频.code.intValue() == upload.getType().intValue()) {
	                        	if(!FileUtil.allowUploadVideoTypeAndSize(suffix,file.getSize())) {
	                        		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxVideoSize").toString());
	    	                		throw CommonException.businessException("文件不合法!请上传视频文件，且大小不超过"+allowSize/1024000L+"M！");
	    	                	}
	                        }
	                        if(FileEnum.FileType.音频.code.intValue() == upload.getType().intValue()) {
	                        	if(!FileUtil.allowUploadAudioTypeAndSize(suffix,file.getSize())) {
	                        		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxAudioSize").toString());
	    	                		throw CommonException.businessException("文件不合法!请上传音频文件，且大小不超过"+allowSize/1024000L+"M！");
	    	                	}
	                        }
	                        myFile.setOldFileName(file.getOriginalFilename());
	                        myFile.setName(upload.getName());
	                        myFile.setModuleName(ModuleEnum.Name.getNameByCode(upload.getModule()));
	                        myFile.setCreateMan(upload.getUserId());
	                        myFile.setCreateAt(new Date());
	                        myFile.setState(CommonEnum.State.启用.code.shortValue());
	                        myFile.setSuffix(suffix);
	                        myFile.setId(RandomUtil.get32UUID());
	                        fileList.add(myFile);
	                    }
	                    else {
		                	throw CommonException.businessException("请选择文件!");
		                }
	                }
	                else {
	                	throw CommonException.businessException("请选择文件!");
	                }
	                //记录上传该文件后的时间  
	                long finaltime = System.currentTimeMillis();  
	                String castTime = String.valueOf((finaltime - pre)/1000);
	                log.info("上传文件用时:"+castTime+"秒");  
	            }  
	              
	        }  
	        webFileService.addBatch(fileList);
	        return new DosserReturnBodyBuilder().collection(urlList).build();  
	    } */
	    

	    
	    @PostMapping("/upload")
		@ApiOperation(value = "批量上传文件", tags = "通用-文件")
	    public DosserReturnBody upload(@RequestParam("file") MultipartFile[] files,
	    		HttpServletRequest request,@Valid WapFileUploadDTO upload) {
	    	List<Map<String,Object>> urlList = new ArrayList<>();
	    	List<com.anmong.core.entity.File> fileList = new ArrayList<>();
	        //记录上传过程起始时的时间，用来计算上传时间  
            long pre = System.currentTimeMillis();  
	        if(files.length > 0) {
	        	for(MultipartFile file : files) {
	        		 //取得当前上传文件的文件名称  
					uploadFile(file, request, upload, urlList, fileList);
			      
			        //记录上传该文件后的时间  
			        long finaltime = System.currentTimeMillis();  
			        String castTime = String.valueOf(finaltime - pre);
			        log.info("上传文件用时:"+castTime+"毫秒");
			        }
	        	}
	        else {
	        	throw CommonException.businessException("请选择文件!");
	        } 
	        webFileService.addBatch(fileList);
	        return new DosserReturnBodyBuilder().collection(urlList).build();  
	    }

	@PostMapping("/upload-one")
	@ApiOperation(value = "单个上传文件", tags = "通用-文件")
	public DosserReturnBody uploadOne(@RequestParam("file") MultipartFile file,
								   HttpServletRequest request,@Valid WapFileUploadDTO upload) {
		List<Map<String,Object>> urlList = new ArrayList<>();
		List<com.anmong.core.entity.File> fileList = new ArrayList<>();
		//记录上传过程起始时的时间，用来计算上传时间
		long pre = System.currentTimeMillis();
		if(!file.isEmpty()) {
			uploadFile(file, request, upload, urlList, fileList);

			//记录上传该文件后的时间
			long finaltime = System.currentTimeMillis();
			String castTime = String.valueOf(finaltime - pre);
			log.info("上传文件用时:"+castTime+"毫秒");
		}
		else {
			throw CommonException.businessException("请选择文件!");
		}
		webFileService.addBatch(fileList);
		return new DosserReturnBodyBuilder().collection(urlList).build();
	}

	private void uploadFile(MultipartFile file, HttpServletRequest request, WapFileUploadDTO upload, List<Map<String, Object>> urlList, List<com.anmong.core.entity.File> fileList) {
		//取得当前上传文件的文件名称
		String  originalFileName= file.getOriginalFilename();
		//如果名称不为“”,说明该文件存在，否则说明该文件不存在
		if(originalFileName.trim() !=""){
            String module = ModuleEnum.Name.getCode(upload.getModule());
            String suffix = originalFileName.substring(originalFileName.lastIndexOf(".")+1,originalFileName.length());
            //重命名上传后的文件名
            String newFileName = module+"_"+ DateUtil.getYYYYmmDDhhMMssSSS(new Date())+ RandomUtil.getFourRandom()+"."+suffix;
            com.anmong.core.entity.File myFile = new com.anmong.core.entity.File();
            myFile.setNewFileName(newFileName);
            myFile.setModuleCode(module);
            myFile.setType(upload.getType());
            myFile.setSuffix(suffix);
            myFile.setOldFileName(file.getOriginalFilename());
            //上传文件开始
            String uploadUrl = "/"+module+"/"+DateUtil.getYYYYmmDD(System.currentTimeMillis())+"/"+newFileName;
            FileUtil.commonUpload(file,myFile,request,uploadUrl);
            myFile.setName(upload.getName());
            myFile.setModuleName(ModuleEnum.Name.getNameByCode(upload.getModule()));
            myFile.setCreateMan(upload.getUserId());
            myFile.setCreateAt(new Date());
            myFile.setState(CommonEnum.State.启用.code.shortValue());
            myFile.setId(RandomUtil.get32UUID());
            urlList.add(MapFactory.of("id", myFile.getId(), "url", myFile.getUrl()));
            fileList.add(myFile);
        }
        else {
            throw CommonException.businessException("请选择文件!");
        }
	}


}
