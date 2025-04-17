package com.blossom.service;


import com.blossom.dto.OrdersSubmitDTO;
import com.blossom.entity.OrderDetail;
import com.blossom.vo.OrderDetailVO;
import com.blossom.vo.OrderSubmitVO;
import com.blossom.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单查询
     * @param userId
     * @return
     */
    List<OrderVO> list(Long userId);

    /**
     * 订单详情查询
     * @param orderId
     * @return
     */
    List<OrderDetailVO> listOrderDetail(Long orderId);

//    /**
//     * 支付成功，修改订单状态
//     * @param outTradeNo
//     */
//    void paySuccess(String outTradeNo);
//
//
//    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;
}
