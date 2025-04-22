package com.blossom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blossom.entity.SeckillVoucher;
import com.blossom.mapper.SeckillVoucherMapper;
import com.blossom.service.ISeckillVoucherService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀优惠券表，与优惠券是一对一关系 服务实现类
 * </p>
 *
 * @author Vast
 */
@Service
public class SeckillVoucherServiceImpl extends ServiceImpl<SeckillVoucherMapper, SeckillVoucher> implements ISeckillVoucherService {

}
