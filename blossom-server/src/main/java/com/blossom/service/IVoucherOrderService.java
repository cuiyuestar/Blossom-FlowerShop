package com.blossom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blossom.result.Result;
import com.blossom.entity.VoucherOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vast

 */
public interface IVoucherOrderService extends IService<VoucherOrder> {

    /**
     * 秒杀下单
     * @param voucherId
     * @return
     */
    Result seckillVoucher(Long voucherId);

    /**
     * 优惠卷下单
     * @param voucherId
     */
    void createVoucherOrder(VoucherOrder voucherId);
}
