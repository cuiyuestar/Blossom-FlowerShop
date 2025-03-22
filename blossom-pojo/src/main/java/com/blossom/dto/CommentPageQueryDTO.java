package com.blossom.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentPageQueryDTO implements Serializable {

    //页码
    private int page;

    //每页记录数
    private int pageSize;

    //鲜花id
    private Long flowerId;


}
