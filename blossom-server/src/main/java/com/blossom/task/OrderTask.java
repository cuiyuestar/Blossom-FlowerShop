package com.blossom.task;

import com.blossom.entity.Orders;
import com.blossom.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;
    /**
     * 处理超时订单的方法
     */
    @Scheduled(cron="0 * * * * ? ") //每分钟触发一次
    public void processTimeoutOrders(){
        log.info("定时处理超时订单:{}", LocalDateTime.now());

        //查询有没有超时订单
        LocalDateTime time=LocalDateTime.now().plusMinutes(-15);//计算超时时间

        List<Orders> ordersList=orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT,time);
        if(ordersList!=null&&ordersList.size()>0){
            for(Orders orders:ordersList){
                orders.setStatus(Orders.CANCELLED); //将订单状态改为“已取消”
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());//设置取消时间
                orderMapper.update(orders);
            }
        }

    }

    /**
     * 处理一直处于派送中的订单 (每天凌晨一点将前一天的派送中订单全部更新为已送达)
     */
    @Scheduled(cron="0 0 1 * * ?") //每天凌晨一点触发一次
    public void processDeliveryOrders(){
        log.info("定时处理处于派送中状态的订单:{}",LocalDateTime.now());

        LocalDateTime time=LocalDateTime.now().plusMinutes(-60);
        //查询处于派送中的订单
        List<Orders> ordersList=orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS,time);
        if(ordersList!=null&&ordersList.size()>0){
            for(Orders orders:ordersList){
                orders.setStatus(Orders.COMPLETED); //将订单状态改为“已完成”
                orderMapper.update(orders);
            }
        }
    }
}
