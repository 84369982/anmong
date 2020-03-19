package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "articles")
@Data
public class Article {
    @Id
    private String id;

    private String title;

    private String titleImg;

    private String content;

    private String txt;

    private Short articleType;

    private Short contentType;

    private Integer readQuantity;

    private Integer forwardQuantity;

    private Integer likeQuantity;

    private Integer commentQuantity;

    private Short isHot;

    private Short isForward;

    private String sourceId;

    private Short state;

    private String createMan;

    private Date createAt;


}