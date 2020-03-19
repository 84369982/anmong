package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "access_log")
@Data
public class AccessLog {
    @Id
    private String id;

    private String userId;

    private String moduleName;

    private String ip;

    private Double longitude;

    private Double latitude;

    private String accessAddress;

    private Date createAt;

}