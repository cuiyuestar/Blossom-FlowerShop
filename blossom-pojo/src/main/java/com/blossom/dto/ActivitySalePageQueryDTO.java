package com.blossom.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActivitySalePageQueryDTO implements Serializable {

    //页码
    private int page;

    //每页记录数
    private int pageSize;

    //活动id
    private Long activityId;


}
