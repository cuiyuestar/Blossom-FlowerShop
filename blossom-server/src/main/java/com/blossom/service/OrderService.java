package com.blossom.service;


import com.blossom.dto.OrdersSubmitDTO;
import com.blossom.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

//    /**
//     * 支付成功，修改订单状态
//     * @param outTradeNo
//     */
//    void paySuccess(String outTradeNo);
//
//
//    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;
}
