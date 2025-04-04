package com.blossom.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ParticipateDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long activityId;

    private Long flowerId;

    private Integer quantity;

    private LocalDateTime createTime;
}
