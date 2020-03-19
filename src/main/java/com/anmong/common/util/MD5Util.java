package com.anmong.common.util;

import java.security.MessageDigest;

public class MD5Util {
	
	    // 得到MD5摘要串
		public static String getMD5Str(String s) {
			// 用作十六进制的数组.
			byte hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			try {
				MessageDigest mdTemp = MessageDigest.getInstance("MD5"
						.toUpperCase());// 使用MD5加密
				byte[] strTemp = s.getBytes();// 把传入的字符串转换成字节数组
				mdTemp.update(strTemp);//
				byte[] md = mdTemp.digest();
				int j = md.length;
				byte str[] = new byte[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);// 返回加密后的字符串.
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}

      }
}
