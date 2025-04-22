package com.blossom.controller.admin;

import com.blossom.entity.Voucher;
import com.blossom.result.Result;
import com.blossom.service.IVoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Vast
 * @version 1.0
 * @function:优惠卷前端控制器
 */
@SuppressWarnings({"all"})
@RestController
@Slf4j
@Api(tags = "优惠劵管理")
@RequestMapping("/admin/voucher")
public class VoucherController {
    @Resource
    private IVoucherService voucherService;

    /**
     * 新增普通券
     * @param voucher 优惠券信息
     * @return 优惠券id
     */
    @PostMapping
    @ApiOperation("新增普通券")
    public Result addVoucher(@RequestBody Voucher voucher) {
        log.info("传入参数，{}",voucher);
        voucherService.save(voucher);
        return Result.success(voucher.getId());
    }

    /**
     * 新增秒杀券
     * @param voucher 优惠券信息，包含秒杀信息
     * @return 优惠券id
     */
    @PostMapping("seckill")
    @ApiOperation("新增秒杀券")
    public Result addSeckillVoucher(@RequestBody Voucher voucher) {
        log.info("传入参数，{}",voucher);
        voucherService.addSeckillVoucher(voucher);
        return Result.success(voucher.getId());
    }
}
