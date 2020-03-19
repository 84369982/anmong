package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "permissions")
@Data
public class Permission {

    @Id
	private String id;

    private String parentId;

    private String name;

    private Short type;
    
    private String menuId;
    
    private String icon;

    private String url;

    private String note;

    private Integer sort;

    private String code;
    
    private Short isRoot;

    private Short state;

    private String createMan;

    private Date createAt;

    
}