package com.blossom.controller.user;


import com.blossom.result.Result;
import com.blossom.service.IVoucherOrderService;
import com.blossom.service.IVoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Vast
 */
@RestController
@RequestMapping("/user/voucher-order")
@Slf4j
@Api(tags = "C端优惠卷相关接口")
public class VoucherOrderController {
    @Resource
    private IVoucherOrderService voucherOrderService;

    @Resource
    private IVoucherService voucherService;

    /**
     * 秒杀下单控制器
     * @param voucherId
     * @return
     */
    @ApiOperation("用户抢购优惠卷")
    @PostMapping("seckill/{id}")
    public Result seckillVoucher(@PathVariable("id") Long voucherId) {
        log.info("传入的参数为:{}",voucherId);
        return voucherOrderService.seckillVoucher(voucherId);
    }

    /**
     * 查询店铺的优惠券列表
     * @param shopId 店铺id
     * @return 优惠券列表
     */
    @ApiOperation("用户查询店铺的优惠券列表")
    @GetMapping("/list/{shopId}")
    public Result queryVoucherOfShop(@PathVariable("shopId") Long shopId) {
        log.info("传入的参数为：{}",shopId);
        return voucherService.queryVoucherOfShop(shopId);
    }
}
