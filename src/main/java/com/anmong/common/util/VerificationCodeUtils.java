package com.anmong.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.anmong.common.message.CommonException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 用于web端生成图片验证码和验证验证码正确性
 * @author songwenlong
 * 2017/9/11
 */
public class VerificationCodeUtils {
	
	private static Logger log = LoggerFactory.getLogger(VerificationCodeUtils.class);

    /**
     * 随机生成背景和字体颜色
     * @param fontColor 字体颜色
     * @param backgroundColor 背景颜色
     * @return
     */
    private static Color getRandomColor(int fontColor, int backgroundColor) {
        Random random=new Random();
        if(fontColor > 255) {
            fontColor = 255;
        }
        if(backgroundColor > 255) {
            backgroundColor=255;
        }
        int r = fontColor+random.nextInt(backgroundColor-fontColor);
        int g = fontColor+random.nextInt(backgroundColor-fontColor);
        int b = fontColor+random.nextInt(backgroundColor-fontColor);
        return new Color(r,g,b);
    }

    /**
     * 创建验证码
     * @param request 当前请求体
     * @param response 当前响应体
     */
    public static void createVerifyCode(HttpServletRequest request,
                                        HttpServletResponse response) {
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-che");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");
        HttpSession session = request.getSession();
        session.removeAttribute("verificationCode");
        //定义长度和宽度
        int width = 60;
        int height = 40;
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random  random = new Random();
        g.setColor(getRandomColor(200,250));
        g.fillRect(0,0,width,height);
        int fontSize = 20;
        g.setFont(new Font("Times New Roman",Font.PLAIN,fontSize));
        g.setColor(getRandomColor(160,200));

        for(int i = 0; i < 155; i++) {
            int x =random.nextInt(width);
            int y =random.nextInt(height);
            int xl =random.nextInt(12);
            int yl =random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);
        }
        String verificationCode = "";
        for(int i=0;i < 4;i++) {
            String rand = String.valueOf(random.nextInt(10));
            verificationCode += rand;
            g.setColor(new Color(20+random.nextInt(110),40+random.nextInt(110),60+random.nextInt(110)));
            g.drawString(rand,13*i+6,26);
        }
        session.setAttribute("verificationCode",verificationCode);
        g.dispose();
        String imageFormat = "jpg";
        try {
            ImageIO.write(image,imageFormat,response.getOutputStream());
        } catch (IOException e) {
            log.error("生成验证码出错:"+e.getMessage());
        }
    }

    /**
     * 验证验证码正确性
     * @param request 当前请求体
     * @param verificationCode 用户输入的验证码
     * @return
     */
    public static Boolean checkVerificationCode( HttpServletRequest request,String verificationCode) {
        HttpSession session = request.getSession();
        String savedCode =(String)session.getAttribute("verificationCode");
        if (StringUtils.isEmpty(savedCode)){
            throw CommonException.businessException("当前验证码已过期，点击图片可重新获取!");
        }
        if(verificationCode.equals(savedCode)){
            session.removeAttribute("verificationCode");
            return true;
        }else{
            return false;
        }
    }





}
