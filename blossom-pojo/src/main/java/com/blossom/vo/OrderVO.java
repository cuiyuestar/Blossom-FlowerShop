package com.blossom.vo;

import com.blossom.entity.OrderDetail;
import com.blossom.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends Orders implements Serializable {

    //订单鲜花信息（以Json格式返回前端）
    //private String orderFlowers;

    //订单详情
    //private List<OrderDetail> orderDetailList;

    private Long orderId;

    private String phone;

    private String address;

    private String username;

    private Integer deliveryStatus;

    private LocalDateTime deliveryTime;


}
