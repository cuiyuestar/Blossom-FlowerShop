package com.blossom.vo;

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
public class ActivitySaleVO implements Serializable {

    //鲜花名称
    private String name;
    //分类id
    private Long categoryId;
    //价格
    private BigDecimal price;
    //图片
    private String image;
    //描述信息
    private String description;
    //0 停售 1 起售
    private Integer status;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

}
