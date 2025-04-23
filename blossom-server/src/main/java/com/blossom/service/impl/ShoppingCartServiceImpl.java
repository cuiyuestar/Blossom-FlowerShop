package com.blossom.service.impl;


import com.blossom.context.BaseContext;
import com.blossom.dto.ShoppingCartDTO;
import com.blossom.entity.Flower;
import com.blossom.entity.ShoppingCart;
import com.blossom.mapper.FlowerMapper;
import com.blossom.mapper.ShoppingCartMapper;
import com.blossom.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private FlowerMapper flowerMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断当前商品是否已经存在于购物车里
        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId();//从ThreadLocal中取出userId(拦截器会拦截用户发来的token
        log.info("当前用户id为：{}",userId);
        //并将其携带的userId保存到ThreadLocal)
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list=shoppingCartMapper.list(shoppingCart);
        //如果已经存在，则增加商品数量，不进行新增操作（update）
        if(list!=null&&list.size()>0){
            ShoppingCart cart =list.get(0);
            cart.setNumber(cart.getNumber()+1); //购物车中该商品数量加1
            shoppingCartMapper.updateNumberById(cart);
        }
        //如果不存在，则添加到购物车，数量默认为1（insert）
        else{
            Long flowerId=shoppingCartDTO.getFlowerId();
            if(flowerId!=null){
                Flower flower=flowerMapper.getById(flowerId); //根据菜品id查询菜品对象，拿到该菜品的全部数据
                shoppingCart.setName(flower.getName());
                shoppingCart.setImage(flower.getImage());
                shoppingCart.setAmount(flower.getPrice());
            }else{
                throw new RuntimeException("不存在此类鲜花");
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 查看购物车
     * @return
     */
    public List<ShoppingCart> showShoppingCart() {
        //获取当前用户的id
        Long userId =BaseContext.getCurrentId();
        ShoppingCart shoppingCart=ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart>list = shoppingCartMapper.list(shoppingCart); //根据用户id查询该用户的购物车
        return list;
    }

    /**
     * 清空购物车
     */
    public void cleanShoppingCart() {
        Long userId=BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }


}
