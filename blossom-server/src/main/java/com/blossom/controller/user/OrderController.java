package com.blossom.controller.user;


import cn.hutool.db.sql.Order;
import com.blossom.context.BaseContext;
import com.blossom.dto.OrdersPaymentDTO;
import com.blossom.dto.OrdersSubmitDTO;
import com.blossom.entity.OrderDetail;
import com.blossom.result.Result;
import com.blossom.service.OrderService;
import com.blossom.vo.OrderDetailVO;
import com.blossom.vo.OrderPaymentVO;
import com.blossom.vo.OrderSubmitVO;
import com.blossom.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userOrderController")
@Api(tags = "用户端相关接口")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单提交
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("订单提交")
    public Result submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        OrderSubmitVO orderSubmitVO =orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单查询
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("订单查询")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "token",
                    value = "Bearer Token",
                    required = true,
                    paramType = "header"
            )
    })
    public Result<List<OrderVO>> listOrder(){
        log.info("订单查询");
        Long userId= BaseContext.getCurrentId();
        log.info("用户id:{}",userId);
        List<OrderVO> orderVOList=orderService.list(userId);
        log.info("订单查询:{}",orderVOList);
        return Result.success(orderVOList);
    }

    /**
     * 查看订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail/{orderId}")
    @ApiOperation("查看订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "token",
                    value = "Bearer Token",
                    required = true,
                    paramType = "header"
            )
    })
    public Result<List<OrderDetailVO>> listOrderDetail(@PathVariable  Long orderId){
        List<OrderDetailVO> orderDetailVOList=orderService.listOrderDetail(orderId);
        log.info("订单详情查询:{}",orderDetailVOList);
        return Result.success(orderDetailVOList);
    }



    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
//    @PutMapping("/payment")
//    @ApiOperation("订单支付")
//    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
//        log.info("订单支付：{}", ordersPaymentDTO);
//        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
//        log.info("生成预支付交易单：{}", orderPaymentVO);
//        return Result.success(orderPaymentVO);
//    }
}
