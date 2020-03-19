package com.anmong.common.util;





import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.anmong.common.config.MyConfig;
import com.anmong.common.message.CommonException;
import com.anmong.core.enums.FileEnum;

public class FileUtil {
	
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 通用上传
	 * @param fileName
	 * @param storeType
	 */
	public static void commonUpload(MultipartFile file,com.anmong.core.entity.File myFile,
			HttpServletRequest request,String uploadUrl) {
		 String suffix = myFile.getOldFileName().substring(myFile.getOldFileName().lastIndexOf(".")+1,myFile.getOldFileName().length());
		 Integer storeType = null;
		 Integer fileType = myFile.getType().intValue();
		 String url = null;
	     if(!FileEnum.isAllowType(fileType)) {
         	throw CommonException.businessException("请上传正确的文件类型");
         }
         if(FileEnum.FileType.图片.code.equals(fileType)) {
         	if(!FileUtil.allowUploadImgTypeAndSize(suffix,file.getSize())) {
         		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxImgSize").toString());
         		throw CommonException.businessException("文件不合法!请上传图片文件，且大小不超过"+allowSize/1024000L+"M！");
         	}
         	storeType = Integer.parseInt(MyConfig.getConfig("system.file.image.storeType"));
         	url = MyConfig.getConfig("system.file.static.image.domain")+uploadUrl;
         }
         if(FileEnum.FileType.视频.code.equals(fileType)) {
         	if(!FileUtil.allowUploadVideoTypeAndSize(suffix,file.getSize())) {
         		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxVideoSize").toString());
         		throw CommonException.businessException("文件不合法!请上传视频文件，且大小不超过"+allowSize/1024000L+"M！");
         	}
         	storeType = Integer.parseInt(MyConfig.getConfig("system.file.video.storeType"));
         	url = MyConfig.getConfig("system.file.static.video.domain")+uploadUrl;
         }
         if(FileEnum.FileType.音频.code.equals(fileType)) {
         	if(!FileUtil.allowUploadAudioTypeAndSize(suffix,file.getSize())) {
         		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxAudioSize").toString());
         		throw CommonException.businessException("文件不合法!请上传音频文件，且大小不超过"+allowSize/1024000L+"M！");
         	}
         	storeType = Integer.parseInt(MyConfig.getConfig("system.file.audio.storeType"));
         	url = MyConfig.getConfig("system.file.static.audio.domain")+uploadUrl;
         }
     	myFile.setStoreType(storeType.shortValue());
		myFile.setSuffix(suffix);
		//存储在本地时不能使用单独的子域名
		if(FileEnum.StoreType.本地.code.equals(storeType)) { 
			String uploadFolder = MyConfig.getConfig("system.file.uploadLocalPath");
			StringBuffer requestUrl = request.getRequestURL();  
			String domainUrl = requestUrl.delete(requestUrl.length() - request.getRequestURI().length(), requestUrl.length()).toString();
			String saveUrl = domainUrl+uploadFolder+uploadUrl;
			myFile.setUrl(saveUrl);
			String localPath =  request.getSession().getServletContext().getRealPath("/");
			String saveFilePath = localPath+uploadFolder.substring(1,uploadFolder.length())+uploadUrl;
			myFile.setPath(saveFilePath);
		}
		else {
			myFile.setPath(uploadUrl);
			myFile.setUrl(url);
		}
		try {
			if(FileEnum.FileType.图片.code.equals(fileType)) {
				//判断图片是否需要缩放
				if(ImageUtil.isNeedCut(file.getSize(),myFile.getSuffix())) {
				    //按像素缩放时不清晰，这里按比例缩放
			    	//FileUtil.cutImage(file,myFile.getNewFileName(),saveFilePath,request);
					File cutFile = ImageUtil.zoomImage(file.getInputStream(),myFile.getNewFileName(),request);
					myFile.setSize(cutFile.length());
					UploadUtil.uploadSelector(storeType,new FileInputStream(cutFile),uploadUrl,request);
					cutFile.delete();
				}
				else {
		    		UploadUtil.uploadSelector(storeType,file.getInputStream(),uploadUrl,request);
		    		myFile.setSize(file.getSize());
		    	}
			}
			else {
	    		UploadUtil.uploadSelector(storeType,file.getInputStream(),uploadUrl,request);
	    		myFile.setSize(file.getSize());
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			throw CommonException.businessException("上传时发生了错误，请重新上传!");
		}

		
		
	}
	
	/**
	 * 通用删除文件
	 */
	public static void commonDelete(String path,Integer storeType) {
		log.info("删除文件:"+path);
		UploadUtil.deleteSelector(storeType, path);
	}
	
	
	/**
	 * 通用判断文件类型和大小方法
	 * @param allowTypeArray
	 * @param type
	 * @param allowSize
	 * @param fileSize
	 * @return
	 */
	public static boolean allowUploadTypeAndSize(String[] allowTypeArray,String type,long allowSize,long fileSize) {
		boolean resutl = false;
		if(fileSize <= allowSize) {
			if(allowTypeArray.length > 0) {
				for(String allowType : allowTypeArray) {
					//转换成小写
					if(allowType.equals(type.toLowerCase())) {
						resutl = true;
						break;
					}
				}
			}
			else {
				resutl = false;
			}
		}
		else {
			resutl = false;
		}
		return resutl;
	}
	
	public static boolean allowUploadImgTypeAndSize(String type,long size) {
		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxImgSize").toString());
		String[] allowTypeArray = MyConfig.getConfig("system.file.allowUploadImgType").split(",");
		boolean resutl = allowUploadTypeAndSize(allowTypeArray,type,allowSize,size);
		return resutl;
	}
	
	public static boolean allowUploadVideoTypeAndSize(String type,long size) {
		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxVideoSize").toString());
		String[] allowTypeArray = MyConfig.getConfig("system.file.allowUploadVideoType").split(",");
		boolean resutl = allowUploadTypeAndSize(allowTypeArray,type,allowSize,size);
		return resutl;
	}
	
	public static boolean allowUploadAudioTypeAndSize(String type,long size) {
		long allowSize = Long.parseLong(MyConfig.getConfig("system.file.allowMaxAudioSize").toString());
		String[] allowTypeArray = MyConfig.getConfig("system.file.allowUploadAudioType").split(",");
		boolean resutl = allowUploadTypeAndSize(allowTypeArray,type,allowSize,size);
		return resutl;
	}
	
	 public static String GetContentType(String fileextname){
         switch (fileextname)
         {   
             default: return "application/octet-stream";
             case "jpeg": return "image/jpeg";
             case "jpg": return "image/jpeg";
             case "js": return "application/x-javascript";
             case "jsp": return "text/html";
             case "gif": return "image/gif";
             case "htm": return "text/html";
             case "html": return "text/html";
             case "asf": return "video/x-ms-asf";
             case "avi": return "video/avi";
             case "bmp": return "application/x-bmp";
             case "asp": return "text/asp";
             case "wma": return "audio/x-ms-wma";
             case "wav": return "audio/wav";
             case "wmv": return "video/x-ms-wmv";
             case "ra": return "audio/vnd.rn-realaudio";
             case "ram": return "audio/x-pn-realaudio";
             case "rm": return "application/vnd.rn-realmedia";
             case "rmvb": return "application/vnd.rn-realmedia-vbr";
             case "xhtml": return "text/html";
             case "png": return "image/png";
             case "ppt": return "application/x-ppt";
             case "tif": return "image/tiff";
             case "tiff": return "image/tiff";
             case "xls": return "application/x-xls";
             case "xlw": return "application/x-xlw";
             case "xml": return "text/xml";
             case "xpl": return "audio/scpls";
             case "swf": return "application/x-shockwave-flash";
             case "torrent": return "application/x-bittorrent";
             case "dll": return "application/x-msdownload";
             case "asa": return "text/asa";
             case "asx": return "video/x-ms-asf";
             case "au": return "audio/basic";
             case "css": return "text/css";
             case "doc": return "application/msword";
             case "exe": return "application/x-msdownload";
             case "mp1": return "audio/mp1";
             case "mp2": return "audio/mp2";
             case "mp2v": return "video/mpeg";
             case "mp3": return "audio/mp3";
             case "mp4": return "video/mpeg4";
             case "mpa": return "video/x-mpg";
             case "mpd": return "application/vnd.ms-project";
             case "mpe": return "video/x-mpeg";
             case "mpeg": return "video/mpg";
             case "mpg": return "video/mpg";
             case "mpga": return "audio/rn-mpeg";
             case "mpp": return "application/vnd.ms-project";
             case "mps": return "video/x-mpeg";
             case "mpt": return "application/vnd.ms-project";
             case "mpv": return "video/mpg";
             case "mpv2": return "video/mpeg";
             case "wml": return "text/vnd.wap.wml";
             case "wsdl": return "text/xml";
             case "xsd": return "text/xml";
             case "xsl": return "text/xml";
             case "xslt": return "text/xml";
             case "htc": return "text/x-component";
             case "mdb": return "application/msaccess";
             case "zip": return "application/zip";
             case "rar": return "application/x-rar-compressed";
             case "001": return "application/x-001";
             case "301": return "application/x-301";
             case "323": return "text/h323";
             case "906": return "application/x-906";
             case "907": return "drawing/907";
             case "a11": return "application/x-a11";
             case "acp": return "audio/x-mei-aac";
             case "ai": return "application/postscript";
             case "aif": return "audio/aiff";
             case "aifc": return "audio/aiff";
             case "aiff": return "audio/aiff";
             case "anv": return "application/x-anv";
             case "awf": return "application/vnd.adobe.workflow";
             case "biz": return "text/xml";
             case "bot": return "application/x-bot";
             case "c4t": return "application/x-c4t";
             case "c90": return "application/x-c90";
             case "cal": return "application/x-cals";
             case "cat": return "application/vnd.ms-pki.seccat";
             case "cdf": return "application/x-netcdf";
             case "cdr": return "application/x-cdr";
             case "cel": return "application/x-cel";
             case "cer": return "application/x-x509-ca-cert";
             case "cg4": return "application/x-g4";
             case "cgm": return "application/x-cgm";
             case "cit": return "application/x-cit";
             case "class": return "java/*";
             case "cml": return "text/xml";
             case "cmp": return "application/x-cmp";
             case "cmx": return "application/x-cmx";
             case "cot": return "application/x-cot";
             case "crl": return "application/pkix-crl";
             case "crt": return "application/x-x509-ca-cert";
             case "csi": return "application/x-csi";
             case "cut": return "application/x-cut";
             case "dbf": return "application/x-dbf";
             case "dbm": return "application/x-dbm";
             case "dbx": return "application/x-dbx";
             case "dcd": return "text/xml";
             case "dcx": return "application/x-dcx";
             case "der": return "application/x-x509-ca-cert";
             case "dgn": return "application/x-dgn";
             case "dib": return "application/x-dib";
             case "dot": return "application/msword";
             case "drw": return "application/x-drw";
             case "dtd": return "text/xml";
             case "dwf": return "application/x-dwf";
             case "dwg": return "application/x-dwg";
             case "dxb": return "application/x-dxb";
             case "dxf": return "application/x-dxf";
             case "edn": return "application/vnd.adobe.edn";
             case "emf": return "application/x-emf";
             case "eml": return "message/rfc822";
             case "ent": return "text/xml";
             case "epi": return "application/x-epi";
             case "eps": return "application/x-ps";
             case "etd": return "application/x-ebx";
             case "fax": return "image/fax";
             case "fdf": return "application/vnd.fdf";
             case "fif": return "application/fractals";
             case "fo": return "text/xml";
             case "frm": return "application/x-frm";
             case "g4": return "application/x-g4";
             case "gbr": return "application/x-gbr";
             case "gcd": return "application/x-gcd";
             case "gl2": return "application/x-gl2";
             case "gp4": return "application/x-gp4";
             case "hgl": return "application/x-hgl";
             case "hmr": return "application/x-hmr";
             case "hpg": return "application/x-hpgl";
             case "hpl": return "application/x-hpl";
             case "hqx": return "application/mac-binhex40";
             case "hrf": return "application/x-hrf";
             case "hta": return "application/hta";
             case "htt": return "text/webviewhtml";
             case "htx": return "text/html";
             case "icb": return "application/x-icb";
             case "ico": return "application/x-ico";
             case "iff": return "application/x-iff";
             case "ig4": return "application/x-g4";
             case "igs": return "application/x-igs";
             case "iii": return "application/x-iphone";
             case "img": return "application/x-img";
             case "ins": return "application/x-internet-signup";
             case "isp": return "application/x-internet-signup";
             case "IVF": return "video/x-ivf";
             case "java": return "java/*";
             case "jfif": return "image/jpeg";
             case "jpe": return "application/x-jpe";
             case "la1": return "audio/x-liquid-file";
             case "lar": return "application/x-laplayer-reg";
             case "latex": return "application/x-latex";
             case "lavs": return "audio/x-liquid-secure";
             case "lbm": return "application/x-lbm";
             case "lmsff": return "audio/x-la-lms";
             case "ls": return "application/x-javascript";
             case "ltr": return "application/x-ltr";
             case "m1v": return "video/x-mpeg";
             case "m2v": return "video/x-mpeg";
             case "m3u": return "audio/mpegurl";
             case "m4e": return "video/mpeg4";
             case "mac": return "application/x-mac";
             case "man": return "application/x-troff-man";
             case "math": return "text/xml";
             case "mfp": return "application/x-shockwave-flash";
             case "mht": return "message/rfc822";
             case "mhtml": return "message/rfc822";
             case "mi": return "application/x-mi";
             case "mid": return "audio/mid";
             case "midi": return "audio/mid";
             case "mil": return "application/x-mil";
             case "mml": return "text/xml";
             case "mnd": return "audio/x-musicnet-download";
             case "mns": return "audio/x-musicnet-stream";
             case "mocha": return "application/x-javascript";
             case "movie": return "video/x-sgi-movie";
             case "mpw": return "application/vnd.ms-project";
             case "mpx": return "application/vnd.ms-project";
             case "mtx": return "text/xml";
             case "mxp": return "application/x-mmxp";
             case "net": return "image/pnetvue";
             case "nrf": return "application/x-nrf";
             case "nws": return "message/rfc822";
             case "odc": return "text/x-ms-odc";
             case "out": return "application/x-out";
             case "p10": return "application/pkcs10";
             case "p12": return "application/x-pkcs12";
             case "p7b": return "application/x-pkcs7-certificates";
             case "p7c": return "application/pkcs7-mime";
             case "p7m": return "application/pkcs7-mime";
             case "p7r": return "application/x-pkcs7-certreqresp";
             case "p7s": return "application/pkcs7-signature";
             case "pc5": return "application/x-pc5";
             case "pci": return "application/x-pci";
             case "pcl": return "application/x-pcl";
             case "pcx": return "application/x-pcx";
             case "pdf": return "application/pdf";
             case "pdx": return "application/vnd.adobe.pdx";
             case "pfx": return "application/x-pkcs12";
             case "pgl": return "application/x-pgl";
             case "pic": return "application/x-pic";
             case "pko": return "application/vnd.ms-pki.pko";
             case "pl": return "application/x-perl";
             case "plg": return "text/html";
             case "pls": return "audio/scpls";
             case "plt": return "application/x-plt";
             case "pot": return "application/vnd.ms-powerpoint";
             case "ppa": return "application/vnd.ms-powerpoint";
             case "ppm": return "application/x-ppm";
             case "pps": return "application/vnd.ms-powerpoint";
             case "pr": return "application/x-pr";
             case "prf": return "application/pics-rules";
             case "prn": return "application/x-prn";
             case "prt": return "application/x-prt";
             case "ps": return "application/x-ps";
             case "ptn": return "application/x-ptn";
             case "pwz": return "application/vnd.ms-powerpoint";
             case "r3t": return "text/vnd.rn-realtext3d";
             case "ras": return "application/x-ras";
             case "rat": return "application/rat-file";
             case "rdf": return "text/xml";
             case "rec": return "application/vnd.rn-recording";
             case "red": return "application/x-red";
             case "rgb": return "application/x-rgb";
             case "rjs": return "application/vnd.rn-realsystem-rjs";
             case "rjt": return "application/vnd.rn-realsystem-rjt";
             case "rlc": return "application/x-rlc";
             case "rle": return "application/x-rle";
             case "rmf": return "application/vnd.adobe.rmf";
             case "rmi": return "audio/mid";
             case "rmj": return "application/vnd.rn-realsystem-rmj";
             case "rmm": return "audio/x-pn-realaudio";
             case "rmp": return "application/vnd.rn-rn_music_package";
             case "rms": return "application/vnd.rn-realmedia-secure";
             case "rmx": return "application/vnd.rn-realsystem-rmx";
             case "rnx": return "application/vnd.rn-realplayer";
             case "rp": return "image/vnd.rn-realpix";
             case "rpm": return "audio/x-pn-realaudio-plugin";
             case "rsml": return "application/vnd.rn-rsml";
             case "rt": return "text/vnd.rn-realtext";
             case "rtf": return "application/msword";
             case "rv": return "video/vnd.rn-realvideo";
             case "sam": return "application/x-sam";
             case "sat": return "application/x-sat";
             case "sdp": return "application/sdp";
             case "sdw": return "application/x-sdw";
             case "sit": return "application/x-stuffit";
             case "slb": return "application/x-slb";
             case "sld": return "application/x-sld";
             case "slk": return "drawing/x-slk";
             case "smi": return "application/smil";
             case "smil": return "application/smil";
             case "smk": return "application/x-smk";
             case "snd": return "audio/basic";
             case "sol": return "text/plain";
             case "sor": return "text/plain";
             case "spc": return "application/x-pkcs7-certificates";
             case "spl": return "application/futuresplash";
             case "spp": return "text/xml";
             case "ssm": return "application/streamingmedia";
             case "sst": return "application/vnd.ms-pki.certstore";
             case "stl": return "application/vnd.ms-pki.stl";
             case "stm": return "text/html";
             case "sty": return "application/x-sty";
             case "svg": return "text/xml";
             case "tdf": return "application/x-tdf";
             case "tg4": return "application/x-tg4";
             case "tga": return "application/x-tga";
             case "tld": return "text/xml";
             case "top": return "drawing/x-top";
             case "tsd": return "text/xml";
             case "txt": return "text/plain";
             case "uin": return "application/x-icq";
             case "uls": return "text/iuls";
             case "vcf": return "text/x-vcard";
             case "vda": return "application/x-vda";
             case "vdx": return "application/vnd.visio";
             case "vml": return "text/xml";
             case "vpg": return "application/x-vpeg005";
             case "vsd": return "application/vnd.visio";
             case "vss": return "application/vnd.visio";
             case "vst": return "application/vnd.visio";
             case "vsw": return "application/vnd.visio";
             case "vsx": return "application/vnd.visio";
             case "vtx": return "application/vnd.visio";
             case "vxml": return "text/xml";
             case "wax": return "audio/x-ms-wax";
             case "wb1": return "application/x-wb1";
             case "wb2": return "application/x-wb2";
             case "wb3": return "application/x-wb3";
             case "wbmp": return "image/vnd.wap.wbmp";
             case "wiz": return "application/msword";
             case "wk3": return "application/x-wk3";
             case "wk4": return "application/x-wk4";
             case "wkq": return "application/x-wkq";
             case "wks": return "application/x-wks";
             case "wm": return "video/x-ms-wm";
             case "wmd": return "application/x-ms-wmd";
             case "wmf": return "application/x-wmf";
             case "wmx": return "video/x-ms-wmx";
             case "wmz": return "application/x-ms-wmz";
             case "wp6": return "application/x-wp6";
             case "wpd": return "application/x-wpd";
             case "wpg": return "application/x-wpg";
             case "wpl": return "application/vnd.ms-wpl";
             case "wq1": return "application/x-wq1";
             case "wr1": return "application/x-wr1";
             case "wri": return "application/x-wri";
             case "wrk": return "application/x-wrk";
             case "ws": return "application/x-ws";
             case "ws2": return "application/x-ws";
             case "wsc": return "text/scriptlet";
             case "wvx": return "video/x-ms-wvx";
             case "xdp": return "application/vnd.adobe.xdp";
             case "xdr": return "text/xml";
             case "xfd": return "application/vnd.adobe.xfd";
             case "xfdf": return "application/vnd.adobe.xfdf";
             case "xq": return "text/xml";
             case "xql": return "text/xml";
             case "xquery": return "text/xml";
             case "xwd": return "application/x-xwd";
             case "x_b": return "application/x-x_b";
             case "x_t": return "application/x-x_t";
         }
         
     }


}
