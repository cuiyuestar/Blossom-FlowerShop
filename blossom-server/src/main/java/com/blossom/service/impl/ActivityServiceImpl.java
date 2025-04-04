package com.blossom.service.impl;


import com.blossom.constant.RedisConstants;
import com.blossom.dto.*;
import com.blossom.entity.Activity;
import com.blossom.entity.ActivitySale;
import com.blossom.enumeration.ActivityChainMarkEnum;
import com.blossom.framework.designPattern.designpattern.chain.AbstractChainContext;
import com.blossom.mapper.ActivityMapper;
import com.blossom.mapper.ActivitySaleMapper;
import com.blossom.result.PageResult;
import com.blossom.service.ActivityService;
import com.blossom.utils.RedisClient;
import com.blossom.vo.ParticipationVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    // 所有依赖字段统一改为final
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivitySaleMapper activitySaleMapper;
    @Autowired
    private RedisClient redisClient;

    private final AbstractChainContext<ActivityDTO> activityDTOAbstractChainContext;
    private final AbstractChainContext<ActivitySaleDetailDTO> activitySaleDTOAbstractChainContext;
    private final AbstractChainContext<ParticipateDTO> activityParticipateChainContext;



    /**
     * 活动分页查询
     * @param activityPageQueryDTO
     * @return
     */
    public PageResult pageQuery(ActivityPageQueryDTO activityPageQueryDTO){
        PageHelper.startPage(activityPageQueryDTO.getPage(),activityPageQueryDTO.getPageSize());
        Page<Activity> page=activityMapper.pageQuery(activityPageQueryDTO);
        return new PageResult (page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询活动（前端通过此接口将活动信息展示出去，但不是通过此接口参与活动）
     * @param id
     * @return
     */
    public Activity getById(Long id) {
        Activity activity=redisClient.queryWithMutex(RedisConstants.ACTIVITY_KEY +id,id,Activity.class,
                activityMapper::getById, RedisConstants.CACHE_ACTIVITY_TTL, TimeUnit.SECONDS, RedisConstants.INIT_RETRY_COUNT);
        return activity;
    }

    /**
     * 创建活动
     * @param activityDTO
     */
    public void create(ActivityDTO activityDTO) {
        //责任链校验活动创建参数
        activityDTOAbstractChainContext.handler(ActivityChainMarkEnum.ACTIVITY_CREATE_FILTER.name(),activityDTO);
        Activity activity =new Activity();
        BeanUtils.copyProperties(activityDTO,activity);
        //缓存预热
        Long activityId=activity.getId();
        Duration duration=Duration.between(activity.getEndTime(),activity.getStartTime());
        redisClient.setWithLogicalExpire(RedisConstants.ACTIVITY_KEY +activityId,activity,duration.getSeconds(),
                TimeUnit.SECONDS);

        activityMapper.insert(activity);
    }

    /**
     * 设置活动商品
     * @param activitySaleDetailDTO
     */
    public void setActivitySale(ActivitySaleDetailDTO activitySaleDetailDTO) {
        //责任链校验活动商品参数
        activitySaleDTOAbstractChainContext.handler(ActivityChainMarkEnum.ACTIVITY_SALE_CREATE_FILTER.name(), activitySaleDetailDTO);
        //设置促销鲜花
        ActivitySale activitySale=new ActivitySale();
        BeanUtils.copyProperties(activitySaleDetailDTO,activitySale);

        //缓存预热
        Long activityId= activitySaleDetailDTO.getActivityId();
        //拿到该促销鲜花所对应的活动，然后获取该活动的持续时间，活动结束，该促销鲜花的缓存也应该随之失效
        Activity activity=activityMapper.getById(activityId);
        Duration duration=Duration.between(activity.getEndTime(),activity.getStartTime());
        //key规则：如果基于多个id确定key，则将其相加后再作为key（优势：节省空间，如果直接拼接，会浪费空间）
        Long real_id=0L;
        real_id=activitySale.getActivityId()+activitySale.getFlowerId();
        String key=RedisConstants.ACTIVITY_SALE_KEY+real_id;
        redisClient.setWithLogicalExpire(key,activitySale,duration.getSeconds(), TimeUnit.SECONDS);

        //存入数据库（真正完成设置）
        activitySaleMapper.insert(activitySale);
    }

    /**
     * 参与活动
     * @param participateDTO
     * @return
     */
    public ParticipationVO participate(ParticipateDTO participateDTO) {

        //责任链校验（判断活动是否正在进行+判断是否超买+判断库存是否充足+库存回滚）
        activityParticipateChainContext.handler(ActivityChainMarkEnum.ACTIVITY_PARTICIPATE_FILTER.name(), participateDTO);

        Activity activity=getById(participateDTO.getActivityId());
        Duration duration=Duration.between(activity.getEndTime(),activity.getStartTime());
        ActivitySaleDTO activitySaleDTO=new ActivitySaleDTO();
        activitySaleDTO.setActivityId(participateDTO.getActivityId());
        activitySaleDTO.setFlowerId(participateDTO.getFlowerId());

        ActivitySale activitySale = redisClient.queryWithMutexAndDTO(RedisConstants.ACTIVITY_SALE_KEY, activitySaleDTO,ActivitySale.class,
                activitySaleMapper::getByActivityIdAndFlowerId,
                duration.getSeconds(), TimeUnit.SECONDS, RedisConstants.INIT_RETRY_COUNT);

        ParticipationVO participationVO = ParticipationVO.builder()
                .content(activity.getContant())
                .limitPer(activity.getLimitPer())
                .originalPrice(activitySale.getOriginalPrice())
                .discountPrice(activitySale.getDiscountPrice())
                .stock(activitySale.getStock())
                .sale(activitySale.getSale())
                .build();

        return participationVO;
    }


}
