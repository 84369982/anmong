package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "files")
@Data
public class File {
	@Id
	private String id;

	private String bizId;

	private String moduleName;

	private String moduleCode;

	private String name;

	private String oldFileName;

	private String newFileName;

	private String url;

	private String path;

	private Short storeType;

	private String suffix;

	private Long size;

	private Short state;

	private Date createAt;

	private String createMan;

	private Short type;


}