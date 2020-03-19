package com.anmong.core.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "article_forward")
@Data
public class ArticleForward {
    @Id
    private String id;

    private String articleId;

    private String comment;

    private String createMan;

    private Date createAt;


}