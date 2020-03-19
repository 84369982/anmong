package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "roles")
@Data
public class Role {
    @Id
    private String id;

    private String name;

    private String code;

    private String note;

    private Short state;

    private String createMan;

    private Date createAt;


}