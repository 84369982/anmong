package com.anmong.common.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.anmong.common.config.MyConfig;

public class ImageUtil {
	
	/**
	 * 检测图片是否需要剪裁
	 */
	public static boolean isNeedCut(long fileSize,String suffix) {
		boolean resutl = false;
		if("gif".equals(suffix.toLowerCase())) {
			resutl = false;
		}
		else {
			long cutMaxImgSize = Long.parseLong(MyConfig.getConfig("system.file.cutMaxImgSize").toString()); 
			if(fileSize >= cutMaxImgSize) {
				resutl = true;
			}
		}
		return resutl;
	}
	
	  /*
     * 图片缩放,w，h为缩放的目标宽度和高度
     * src为源文件目录，dest为缩放后保存目录
     */
    public static long cutImage(MultipartFile file,String newFileName,String dest,HttpServletRequest request) throws IOException {
    	int width = Integer.parseInt(MyConfig.getConfig("system.file.cutImgWidth"));
    	int height = Integer.parseInt(MyConfig.getConfig("system.file.cutImgHeight"));
    	//将图片先保存到临时路径
    	String tempUploadFolder = MyConfig.getConfig("system.file.uploadTempPath");
		String tempFolder = request.getSession().getServletContext().getRealPath("/") + tempUploadFolder;
		File tempDirecotory = new File(tempFolder);
		if(!tempDirecotory.isDirectory()) {
			tempDirecotory.mkdirs();
		}
		String saveFilePath = tempDirecotory+"\\"+ newFileName;
		File saveFile = new File(saveFilePath);
		saveFile.setExecutable(false);
		file.transferTo(saveFile);
        double wr=0,hr=0;
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(saveFile); //读取图片
        Image Itemp = bufImg.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);//设置缩放目标图片模板
        
        wr=width*1.0/bufImg.getWidth();     //获取缩放比例
        hr=height*1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        ImageIO.write((BufferedImage) Itemp,newFileName.substring(newFileName.lastIndexOf(".")+1), destFile); //写入缩减后的图片
        long newfileSize = 0;
        newfileSize = destFile.length();
        saveFile.delete();
        return newfileSize;
    }
    
    /*
     * 图片按比率缩放
     * size为文件大小
     */
    public static File zoomImage(InputStream inputStream,String newFileName,HttpServletRequest request) throws IOException{
    	//将图片先保存到临时路径
    	String tempUploadFolder = MyConfig.getConfig("system.file.uploadTempPath");
		String tempFolder = request.getSession().getServletContext().getRealPath("/") + tempUploadFolder;
		File tempDirecotory = new File(tempFolder);
		if(!tempDirecotory.isDirectory()) {
			tempDirecotory.mkdirs();
		}

        Double zoomFileSize = Double.parseDouble(MyConfig.getConfig("system.file.zoomImgSize").toString());
        
        BufferedImage bufImg = ImageIO.read(inputStream);
        Image Itemp = bufImg.getScaledInstance(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.SCALE_SMOOTH);
            
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(zoomFileSize, zoomFileSize), null);
        Itemp = ato.filter(bufImg, null);
        
        File destFile = new File(tempFolder+"/"+newFileName);
     
        ImageIO.write((BufferedImage) Itemp,newFileName.substring(newFileName.lastIndexOf(".")+1), destFile);
        return destFile;
    }

}
