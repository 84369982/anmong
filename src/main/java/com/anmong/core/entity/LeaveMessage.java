package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "leave_message")
@Data
public class LeaveMessage {
    @Id
    private String id;

    private String userId;

    private String replay;

    private Short type;

    private String content;

    private String createMan;

    private Date createAt;


}