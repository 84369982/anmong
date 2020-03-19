package com.anmong.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;






public class SqlBuilder {
	
	private Map<String, Object> predicate = new HashMap<>();
	
	private List<String> whereList = new ArrayList<>();
	
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	
	public SqlBuilder() {
		
	}
	
	public SqlBuilder update(Object entity) {
		predicate = objectToMap(entity);
		return this;
	}
	
	public SqlBuilder where(String where,String condition) {
		if(!StringUtils.isEmpty(condition)) {
			whereList.add(where+condition);
		}
		
		return this;
	}
	
	public SqlBuilder orderBy(String orderBy,String sortType) {
		predicate.put("orderBy", orderBy+" "+sortType);
		return this;
	}
	
	public SqlBuilder groupBy(String groupBy) {
		predicate.put("groupBy", groupBy);
		return this;
	}
	
	public SqlBuilder limit(String limit) {
		predicate.put("limit", limit);
		return this;
		
	}
	
	public Map<String, Object> build(){
		predicate.put("where", whereList);
		return this.predicate;
	}
	
	public List<String> buildWhere(){
		return whereList;
	} 
	
	public static String equal(Object parameter) {
		if(StringUtils.isEmpty(parameter)) {
			return null;
		}
		if(isNumber(parameter)) {
			return " = "+parameter;
		}
		else if(parameter instanceof Date) {
			return " = '"+DateUtil.getYYYYmmDDhhMMss((Date)parameter)+"'";
		}
		else {
			return " = '"+parameter+"'";
		}
		
	}
	
	public static String notEqual(Object parameter) {
		if(StringUtils.isEmpty(parameter)) {
			return null;
		}
		if(isNumber(parameter)) {
			return " != "+parameter;
		}
		else if(parameter instanceof Date) {
			return " != '"+DateUtil.getYYYYmmDDhhMMss((Date)parameter)+"'";
		}
		else {
			return " != '"+parameter+"'";
		}
		
	}
	
	public static String Greater(Object parameter) {
		if(StringUtils.isEmpty(parameter)) {
			return null;
		}
		if(isNumber(parameter)) {
			return " > "+parameter;
		}
		else if(parameter instanceof Date) {
			return " > '"+DateUtil.getYYYYmmDDhhMMss((Date)parameter)+"'";
		}
		else {
			return " > '"+parameter+"'";
		}
	}
	
	public static String Less(Object parameter) {
		if(StringUtils.isEmpty(parameter)) {
			return null;
		}
		if(isNumber(parameter)) {
			return " < "+parameter;
		}
		else if(parameter instanceof Date) {
			return " < '"+DateUtil.getYYYYmmDDhhMMss((Date)parameter)+"'";
		}
		else {
			return " < '"+parameter+"'";
		}
	}
	
	public static String equealOrGreaterThan(Object parameter) {
		if(StringUtils.isEmpty(parameter)) {
			return null;
		}
		if(isNumber(parameter)) {
			return " >= "+parameter;
		}
		else if(parameter instanceof Date) {
			return " >= '"+DateUtil.getYYYYmmDDhhMMss((Date)parameter)+"'";
		}
		else {
			return " >= '"+parameter+"'";
		}
	}
	
	public static String equealOrLessThan(Object parameter) {
		if(StringUtils.isEmpty(parameter)) {
			return null;
		}
		if(isNumber(parameter)) {
			return " <= "+parameter;
		}
		else if(parameter instanceof Date) {
			return " <= '"+DateUtil.getYYYYmmDDhhMMss((Date)parameter)+"'";
		}
		else {
			return " <= '"+parameter+"'";
		}	
	}
	
	public static String in(String... parameters) {
		StringBuilder in = new StringBuilder(" IN (");
		for(String parameter : parameters) {
			in.append("'"+parameter+"',");
		}
		//删除最后一个","
		in.deleteCharAt(in.length()-1);
		in.append(")");
		return in.toString();
	}
	
	public static String in(List<String> parameters) {
		StringBuilder in = new StringBuilder(" IN (");
		for(String parameter : parameters) {
			in.append("'"+parameter+"',");
		}
		//删除最后一个","
		in.deleteCharAt(in.length()-1);
		in.append(") ");
		return in.toString();
	}
	
	public static String like(String parameter) {
		if(!StringUtils.isEmpty(parameter)) {
			return " LIKE '%"+parameter+"%' ";
		}
		else {
			return null;
		}
		
	}
	
	public static String notNull() {
		return " IS NOT NULL ";
	}
	
	public static String isNull() {
		return " IS NULL ";
	}
	
	public static Map<String, Object> objectToMap(Object obj){    
        if(obj == null){    
            return null;    
        }   
  
        Map<String, Object> map = new HashMap<String, Object>();    
  
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            try {
            	//当值为空时设置null值不更新此字段;
				map.put(field.getName(), StringUtils.isEmpty(field.get(obj)) ? null : field.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				return null;
			}  
        }    
        return map;  
    }   
	
	public static boolean isNumber(Object parameter) {
		if(parameter instanceof Integer
				|| parameter instanceof Double
				|| parameter instanceof Short
				|| parameter instanceof Long
				) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
