package com.blossom.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ActivityDTO implements Serializable {


    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private String contant;


}
