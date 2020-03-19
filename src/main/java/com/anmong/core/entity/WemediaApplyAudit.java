package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "wemedia_apply_audit")
@Data
public class WemediaApplyAudit {
    @Id
    private String id;

    private String wemediaApplayId;

    private Short isPass;

    private String opinion;

    private String createMan;

    private Date createAt;


}