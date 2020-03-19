package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "likes")
@Data
public class Like {
    @Id
    private String id;

    private Short type;

    private String bizId;

    private String createMan;

    private Date createAt;


}