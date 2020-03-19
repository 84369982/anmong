package com.anmong.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 将毫秒时间数据转换成string日期型数据
 * 
 * */
public class DateUtil {
  
	/**
	 * 转换成yyyy-MM-dd型
	 * 
	 * */
	public static String getYYYYmmDD(long millisecond){
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    String strDate=sdf.format(new Date(millisecond));
		return strDate;
	}
	
	
	/**
	 * 转换成yyyy-MM-dd HH:mm:ss型
	 * 
	 * */
	public static String getYYYYmmDDhhMMss(long millisecond){
	    long longDate=(long) millisecond;
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String strDate=sdf.format(new Date(longDate));
		return strDate;
	}
	
	/**
	 * 转换成yyyy-MM-dd HH:mm:ss型
	 * 
	 * */
	public static String getYYYYmmDDhhMMss(Date date){
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String strDate=sdf.format(date);
		return strDate;
	}
	
	/**
	 * 转换成yyyy-MM-dd HH:mm:ss型
	 * 
	 * */
	public static String getYYYYmmDDhhMMssSSS(Date date){
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    String strDate=sdf.format(date);
		return strDate;
	}
	
	/**
	 * 转换成yyyy年MM月dd日
	 * @author lfg
	 * @param millisecond
	 * @return
	 * String
	 */
	public static String getYYYYnmmyDDr(long millisecond){
	    long longDate=(long) millisecond;
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
	    String strDate=sdf.format(new Date(longDate));
		return strDate;
	}
	
	/**
	 * 转换成MM-dd型
	 * @author lfg 2016年8月13日
	 * @param millisecond
	 * @return
	 * String
	 */
	public static String getMMdd(long millisecond){
	    long longDate=(long) millisecond;
	    SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
	    String strDate=sdf.format(new Date(longDate));
		return strDate;
	}
	
   public static Date getTodayBegin() {  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    }  
  
    public static Date getTodayEnd() {  
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        todayEnd.set(Calendar.MILLISECOND, 999);  
        return todayEnd.getTime();  
    }  
	
}
