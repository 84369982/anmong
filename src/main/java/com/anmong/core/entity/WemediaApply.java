package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "wemedia_apply")
@Data
public class WemediaApply {
    @Id
    private String id;

    private String reason;

    private Short state;

    private String createMan;

    private Date createAt;

}