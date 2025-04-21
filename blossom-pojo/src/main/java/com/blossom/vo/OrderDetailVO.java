package com.blossom.vo;

import com.blossom.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO  implements Serializable {

    private Long id;

    //名称(用户真名)
    private String name;

    //鲜花名称
    private String flowerName;

    //数量
    private Integer number;

    //金额
    private BigDecimal amount;

    //图片
    private String image;


}
