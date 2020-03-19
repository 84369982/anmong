package com.anmong.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "users")
@Data
public class User{
    /**
	 * 
	 */

	@Id
	private String id;

    private String username;

    private String password;

    private String phone;

    private Short sex;

    private String nickname;

    private String headUrl;

    private String city;

    private Date lastLogin;

    private Integer loginTimes;

    private Short type;

    private Short grade;
    
    private Date birthday;
    
    private String summary;

    private Integer fans;

    private Short state;

    private String createMan;

    private Date createAt;


    
    
}