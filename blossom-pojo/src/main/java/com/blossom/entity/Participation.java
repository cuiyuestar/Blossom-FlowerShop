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
public class Participation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long activityId;

    private Long userId;

    private Integer quantity;

    private Long orderId;

    private LocalDateTime createTime;



}
