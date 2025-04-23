package com.blossom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blossom.entity.Voucher;
import com.blossom.result.Result;

/**
 * @author Vast
 * @version 1.0
 * @function:优惠卷相关接口
 */
@SuppressWarnings({"all"})
public interface IVoucherService extends IService<Voucher> {
    /**
     *查询店铺的优惠券列表
     * @param shopId
     * @return
     */
    Result queryVoucherOfShop(Long shopId);
    /**
     * 新增秒杀卷
     * @param voucher
     */
    void addSeckillVoucher(Voucher voucher);
}
