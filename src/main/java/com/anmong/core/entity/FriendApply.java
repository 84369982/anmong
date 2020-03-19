package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author songwenlong
 * 2018/3/16
 */
@Table(name = "friend_apply")
@Data
public class FriendApply {

    @Id
    private String id;

    private String friendId;

    private String content;

    private Short state;

    private String friendNote;

    private String createManNote;

    private String createMan;

    private Date createAt;
}
