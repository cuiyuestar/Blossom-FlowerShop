package com.blossom.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ActivitySaleDetailDTO implements Serializable {


    private Long activityId;

    private Long flowerId;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    private Integer stock;

    private Integer sale; //已售数量

}
