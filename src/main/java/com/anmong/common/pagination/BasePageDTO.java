package com.anmong.common.pagination;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BasePageDTO {
	
	public void init() {
		if(pageNO == null) {
			pageNO = 1;
		}
		if(pageSize == null) {
			pageSize = 10;
		}
	}
	
	private Integer pageNO = 1;
	private Integer pageSize = 10;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	private String createMan;
	private String state;
	public Integer getPageNO() {
		return pageNO;
	}
	public void setPageNO(Integer pageNO) {
		this.pageNO = pageNO;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCreateMan() {
		return createMan;
	}
	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	



}
