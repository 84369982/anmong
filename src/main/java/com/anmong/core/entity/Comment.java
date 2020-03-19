package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "comments")
@Data
public class Comment {
    @Id
    private String id;

    private String bizId;

    private Short commentType;

    private String parentId;

    private Short contentType;

    private String content;

    private String url;

    private Integer likeQuantity;

    private String createMan;

    private Short state;

    private Date createAt;


}