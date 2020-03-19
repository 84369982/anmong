package com.anmong.common.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
	
	   public static String getFourRandom() {
	        Random random = new Random();
	        String fourRandom = random.nextInt(10000) + "";
	        int randLength = fourRandom.length();
	        if (randLength < 4) {
	            fourRandom = String.format("%04d", Integer.parseInt(fourRandom));
	        }
	        return fourRandom;
	    }
	   
	   public static String get32UUID() {
		   String uuid = UUID.randomUUID().toString(); //获取UUID并转化为String对象  
	       uuid = uuid.replace("-", "");
	       return uuid;
	   }

}
