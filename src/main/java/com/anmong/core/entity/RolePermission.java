package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "role_permission")
@Data
public class RolePermission {
    @Id
    private String id;

    private String roleId;

    private String permissionId;

    private String createMan;

    private Date createAt;


}