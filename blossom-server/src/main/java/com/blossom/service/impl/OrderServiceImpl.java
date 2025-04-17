package com.blossom.service.impl;

import com.blossom.constant.MessageConstant;
import com.blossom.context.BaseContext;
import com.blossom.dto.OrdersSubmitDTO;
import com.blossom.entity.*;
import com.blossom.enumeration.OrderChainMarkEnum;
import com.blossom.exception.AddressBookBusinessException;
import com.blossom.framework.designPattern.designpattern.chain.AbstractChainContext;
import com.blossom.mapper.*;
import com.blossom.service.OrderService;
import com.blossom.vo.OrderDetailVO;
import com.blossom.vo.OrderSubmitVO;
import com.blossom.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private UserMapper userMapper;

    private final AbstractChainContext<OrdersSubmitDTO> orderSubmitAbstractChainContext;
    private final AbstractChainContext<OrderDetail> orderDetailAbstractChainContext;


    /**
     * 订单提交
     * @param ordersSubmitDTO
     * @return
     */
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        //责任链校验订单数据
        orderSubmitAbstractChainContext.handler(OrderChainMarkEnum.ORDER_SUBMIT_FILTER.name(),ordersSubmitDTO);
        //处理各种业务异常（地址簿为空、购物车为空）
        AddressBook addressBook=addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if(addressBook==null){
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        //查询用户购物车记录(根据用户id)
        Long userId= BaseContext.getCurrentId();
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList=shoppingCartMapper.list(shoppingCart);
        if(shoppingCartList==null||shoppingCartList.size()==0){
            throw new AddressBookBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        //创建订单
        Orders orders=new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setNumber(String.valueOf(System.currentTimeMillis()));//用时间戳作为订单号
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(Orders.PENDING_PAYMENT); //设置订单状态为"待付款"
        orders.setPayStatus(Orders.UN_PAID);
        orders.setUserId(userId); //判断当前订单是属于哪个用户的
        orders.setAddress(addressBook.getDetail());
        orders.setPhone(addressBook.getPhone()); //通过addressBook对象获取地址和手机号

        orderMapper.insert(orders);

        List<OrderDetail> orderDetailList=new ArrayList<>();
        //向订单明细表插入n条数据 (一个订单对应多个订单明细)
        for(ShoppingCart cart:shoppingCartList){ //遍历购物车集合里的商品数据
            OrderDetail orderDetail=new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);

            //责任链校验订单明细数据
            orderDetailAbstractChainContext.handler(OrderChainMarkEnum.ORDER_DETAIL_FILTER.name(),orderDetail);

            orderDetail.setOrderId(orders.getId()); //设置订单明细所关联的订单的id
            orderDetailList.add(orderDetail);
        }
        //将多个订单明细批量插入
        orderDetailMapper.insertBatch(orderDetailList);

        //清空购物车（根据用户id）
        shoppingCartMapper.deleteByUserId(userId);

        //封装VO返回结果
        OrderSubmitVO orderSubmitVO=OrderSubmitVO.builder()
                .id(orders.getId())
                .orderAmount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .build();

        return orderSubmitVO;
    }


    /**
     * 订单查询
     * @param userId
     * @return
     */
    public List<OrderVO> list(Long userId) {
        List<Orders> orderList = orderMapper.getByUserId(userId);
        List<OrderVO> orderVOList=new ArrayList<>();
            for(Orders order:orderList){

                OrderVO orderVO=new OrderVO();
                orderVO.setOrderId(order.getId());
                orderVO.setPhone(order.getPhone());
                orderVO.setAddress(order.getAddress());
                orderVO.setDeliveryStatus(order.getDeliveryStatus());
                orderVO.setDeliveryTime(order.getDeliveryTime());
                orderVO.setUsername(order.getUserName());

                orderVOList.add(orderVO);
            }
        return orderVOList;
    }

    /**
     * 订单明细查询
     * @param orderId
     * @return
     */
    public List<OrderDetailVO> listOrderDetail(Long orderId) {
        log.info("订单明细查询service层方法启动:{}",orderId);
        List<OrderDetailVO> orderDetailVOList=new ArrayList<>();
        List<OrderDetail> orderDetailList=orderDetailMapper.listOrderDetail(orderId);
        for(OrderDetail orderDetail:orderDetailList){
            OrderDetailVO orderDetailVO=new OrderDetailVO();

            orderDetailVO.setNumber(orderDetail.getNumber());
            orderDetailVO.setAmount(orderDetail.getAmount());
            orderDetailVO.setImage(orderDetail.getImage());
            orderDetailVO.setName(orderDetail.getName());

            log.info("orderDetailVO:{}",orderDetailVO);

            orderDetailVOList.add(orderDetailVO);

            log.info("orderDetailVOList:{}",orderDetailVOList);
        }
        return orderDetailVOList;
    }


//
//    /**
//     * 订单支付
//     *
//     * @param ordersPaymentDTO
//     * @return
//     */
//    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
//        // 当前登录用户id
//        Long userId = BaseContext.getCurrentId();
//        User user = userMapper.getById(userId);
//
//        //调用微信支付接口，生成预支付交易单
//        JSONObject jsonObject = weChatPayUtil.pay(
//                ordersPaymentDTO.getOrderNumber(), //商户订单号
//                new BigDecimal(0.01), //支付金额，单位 元
//                "苍穹外卖订单", //商品描述
//                user.getOpenid() //微信用户的openid
//        );
//
//        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
//            throw new OrderBusinessException("该订单已支付");
//        }
//
//        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
//        vo.setPackageStr(jsonObject.getString("package"));
//
//        return vo;
//    }
//
//
//
//    /**
//     * 支付成功，修改订单状态
//     *
//     * @param outTradeNo
//     */
//    public void paySuccess(String outTradeNo) {
//
//        // 根据订单号查询订单
//        Orders ordersDB = orderMapper.getByNumber(outTradeNo);
//
//        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
//        Orders orders = Orders.builder()
//                .id(ordersDB.getId())
//                .status(Orders.TO_BE_CONFIRMED)
//                .payStatus(Orders.PAID)
//                .checkoutTime(LocalDateTime.now())
//                .build();
//
//        orderMapper.update(orders);
//    }
}
