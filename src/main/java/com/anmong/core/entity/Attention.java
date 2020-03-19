package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "attentions")
@Data
public class Attention {
    @Id
    private String id;

    private String userId;

    private String createMan;

    private Date createAt;

}