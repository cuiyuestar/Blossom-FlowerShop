package com.blossom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单明细
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //名称(用户真名)
    private String name;

    //订单id
    private Long orderId;

    //鲜花id
    private Long FlowerId;

    //数量
    private Integer number;

    //金额
    private BigDecimal amount;

    //图片
    private String image;


}
