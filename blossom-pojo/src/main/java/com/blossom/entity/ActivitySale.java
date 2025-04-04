package com.blossom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySale implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long activityId;

    private Long flowerId;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    private Integer stock;

    private Integer sale; //已售数量

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer version; //版本号（用于乐观锁）


}
