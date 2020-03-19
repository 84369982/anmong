package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "user_role")
@Data
public class UserRole {
    @Id
    private String id;

    private String userId;

    private String roleId;

    private String createMan;

    private Date createAt;

}