package com.blossom.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommentDTO implements Serializable {

    //评论id
    private Long id;

    private Long userId;

    private Long flowerId;

    //评价等级
    private Integer rating;

    //评论内容
    private String content;

    private Date createTime;


}
