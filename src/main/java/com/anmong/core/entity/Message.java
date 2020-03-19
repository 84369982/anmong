package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author songwenlong
 * 2018/3/16
 */
@Table(name = "messages")
@Data
public class Message {

    @Id
    private String id;

    private String targetUserId;

    private Short type;

    private Short bizType;

    private String content;

    private Short isRead;

    private String createMan;

    private String createManHeadUrl;

    private String createManNickname;

    private Date createAt;
}
