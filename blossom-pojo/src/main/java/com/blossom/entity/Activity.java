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
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    //活动简介
    private String contant;

    //限购数量
    private Integer limitPer;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
