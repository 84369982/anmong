package com.anmong.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUtil {
	
	private static Logger log = LoggerFactory.getLogger(ObjectUtil.class);
	
	   public static void transMapToBean(Map<String, Object> map, Object obj) {
	        if (map == null || obj == null) {
	            return;
	        }
	        try {
	            BeanUtils.populate(obj, map);
	        } catch (Exception e) {
	        	log.error("map转换Object失败 " + e);
	        }
	    }
	    /**
	     * Bean -->Map  2: 利用org.apache.commons.beanutils 工具类实现 Bean -->   Map
	     * @param obj 要转换的实体
	     * @return
	     */
	    public static Map<String, String>  transBeanToMap( Object obj) {
	        if ( obj == null) {
	            return null;
	        }
	        Map<String, String> map=null;
	        try {
	            map = BeanUtils.describe(obj);
	        } catch (Exception e) {
	        	log.error("Object转换map失败 " + e);
	        }
	        return map;
	    }
	    
	    public static <T> List<T> mapListToBeanList(List<Map<String,Object>> mapList,Class<T> clazz) {
	    	List<T> beanList  = new ArrayList<>();
	    	T obj = null;
			try {
				obj = clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(mapList != null && !mapList.isEmpty()) {
	    		for(Map<String,Object> map : mapList) {
	    			transMapToBean(map,obj);
	    			beanList.add(obj);
	    		}
	    	}
	    	return beanList;
	    	
	    }

}
